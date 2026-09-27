package learn.manish.creditCard.service;

import learn.manish.creditCard.dao.CCCrudDao;
import learn.manish.creditCard.dao.CCValidateDao;
import learn.manish.creditCard.exceptions.CCInvalidException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CCServiceImplTest {

    private static final String CARD_NUMBER = "1234567890123456";

    @Mock
    private CCValidateDao ccValidateDao;

    @Mock
    private CCCrudDao ccCrudDao;

    private CCServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CCServiceImpl(ccValidateDao, ccCrudDao);
    }

    @Nested
    class ValidateCard {

        @Test
        void returnsCardWhenNumberExists() {
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(true);

            CreditCard result = service.validateCC(CARD_NUMBER);

            assertEquals(CARD_NUMBER, result.getCcNumber());
            assertEquals("Credit Card number is valid & in database", result.getMessage());
            verify(ccValidateDao).doesCCExistsInDB(CARD_NUMBER);
            verifyNoInteractions(ccCrudDao);
        }

        @Test
        void throwsNotFoundWhenNumberDoesNotExist() {
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(false);

            assertThrows(CCNotFoundException.class, () -> service.validateCC(CARD_NUMBER));
            verify(ccValidateDao).doesCCExistsInDB(CARD_NUMBER);
        }

        @Test
        void rejectsNullShortLongAndNonNumericNumbersWithoutCallingDao() {
            assertThrows(CCInvalidException.class, () -> service.validateCC(null));
            assertThrows(CCInvalidException.class, () -> service.validateCC("123"));
            assertThrows(CCInvalidException.class, () -> service.validateCC("12345678901234567"));
            assertThrows(CCInvalidException.class, () -> service.validateCC("123456789012345X"));

            verifyNoInteractions(ccValidateDao, ccCrudDao);
        }
    }

    @Nested
    class AddCard {

        @Test
        void savesNewCard() {
            CreditCard card = card(CARD_NUMBER);
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(false);
            when(ccCrudDao.saveCC(card)).thenReturn(1);

            assertEquals(1, service.addCC(card));
            verify(ccValidateDao).doesCCExistsInDB(CARD_NUMBER);
            verify(ccCrudDao).saveCC(card);
        }

        @Test
        void returnsZeroWithoutSavingExistingCard() {
            CreditCard card = card(CARD_NUMBER);
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(true);

            assertEquals(0, service.addCC(card));
            verify(ccCrudDao, never()).saveCC(card);
        }

        @Test
        void rejectsNullCardWithoutCallingDao() {
            CCInvalidException exception = assertThrows(CCInvalidException.class, () -> service.addCC(null));

            assertEquals("Credit card details are required", exception.getMessage());
            verifyNoInteractions(ccValidateDao, ccCrudDao);
        }

        @Test
        void rejectsMalformedNumberWithoutCallingDao() {
            assertThrows(CCInvalidException.class, () -> service.addCC(card("invalid")));
            verifyNoInteractions(ccValidateDao, ccCrudDao);
        }
    }

    @Nested
    class GetCardDetails {

        @Test
        void returnsFoundCardAndAddsMessage() {
            CreditCard card = card(CARD_NUMBER);
            when(ccCrudDao.findCCById(CARD_NUMBER)).thenReturn(card);

            CreditCard result = service.getCreditCardDetails(CARD_NUMBER);

            assertSame(card, result);
            assertEquals("Credit Card Number found in database", result.getMessage());
            verify(ccCrudDao).findCCById(CARD_NUMBER);
        }

        @Test
        void throwsNotFoundWhenDaoReturnsNull() {
            when(ccCrudDao.findCCById(CARD_NUMBER)).thenReturn(null);

            assertThrows(CCNotFoundException.class, () -> service.getCreditCardDetails(CARD_NUMBER));
        }

        @Test
        void rejectsMalformedNumberBeforeLookup() {
            assertThrows(CCInvalidException.class, () -> service.getCreditCardDetails("123"));
            verifyNoInteractions(ccCrudDao);
        }
    }

    @Test
    void getAllCCReturnsDaoResults() {
        List<CreditCard> cards = List.of(card(CARD_NUMBER), card("2345678901234567"));
        when(ccCrudDao.getAllCCDetails()).thenReturn(cards);

        assertSame(cards, service.getAllCC());
        verify(ccCrudDao).getAllCCDetails();
    }

    @Nested
    class ModifyCard {

        @Test
        void updatesExistingCard() {
            CreditCard card = card(CARD_NUMBER);
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(true);
            when(ccCrudDao.updateCCDetails(card)).thenReturn(card);

            assertSame(card, service.modifyCC(card));
            verify(ccValidateDao).doesCCExistsInDB(CARD_NUMBER);
            verify(ccCrudDao).updateCCDetails(card);
        }

        @Test
        void doesNotUpdateMissingCard() {
            CreditCard card = card(CARD_NUMBER);
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(false);

            assertThrows(CCNotFoundException.class, () -> service.modifyCC(card));
            verify(ccCrudDao, never()).updateCCDetails(card);
        }

        @Test
        void rejectsNullCard() {
            assertThrows(CCInvalidException.class, () -> service.modifyCC(null));
            verifyNoInteractions(ccValidateDao, ccCrudDao);
        }
    }

    @Nested
    class RemoveCard {

        @Test
        void deletesExistingCard() {
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(true);
            when(ccCrudDao.deleteCC(CARD_NUMBER)).thenReturn(1);

            assertEquals(1, service.removeCC(CARD_NUMBER));
            verify(ccValidateDao).doesCCExistsInDB(CARD_NUMBER);
            verify(ccCrudDao).deleteCC(CARD_NUMBER);
        }

        @Test
        void doesNotDeleteMissingCard() {
            when(ccValidateDao.doesCCExistsInDB(CARD_NUMBER)).thenReturn(false);

            assertThrows(CCNotFoundException.class, () -> service.removeCC(CARD_NUMBER));
            verify(ccCrudDao, never()).deleteCC(CARD_NUMBER);
        }

        @Test
        void rejectsMalformedNumber() {
            assertThrows(CCInvalidException.class, () -> service.removeCC("invalid"));
            verifyNoInteractions(ccValidateDao, ccCrudDao);
        }
    }

    @Test
    void clearCacheCompletesWithoutDaoCalls() {
        assertDoesNotThrow(service::clearCache);
        verifyNoInteractions(ccValidateDao, ccCrudDao);
    }

    private static CreditCard card(String number) {
        CreditCard card = new CreditCard();
        card.setCcNumber(number);
        card.setCcName("Test Bank");
        card.setCcType("Visa");
        return card;
    }
}
