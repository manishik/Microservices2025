package learn.manish.creditCard.service;

import learn.manish.creditCard.dao.CCCrudDao;
import learn.manish.creditCard.dao.CCValidateDao;
import learn.manish.creditCard.model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(CCServiceCacheTest.CacheTestConfiguration.class)
class CCServiceCacheTest {

    private static final String CARD_NUMBER = "1234567890123456";

    @Autowired
    private CCService service;

    @Autowired
    private CCCrudDao ccCrudDao;

    @Autowired
    private CCValidateDao ccValidateDao;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void resetState() {
        reset(ccCrudDao, ccValidateDao);
        for (String name : cacheManager.getCacheNames()) {
            Objects.requireNonNull(cacheManager.getCache(name)).clear();
        }
    }

    @Test
    void getCreditCardDetailsUsesCacheForRepeatedLookup() {
        CreditCard card = card();
        when(ccCrudDao.findCCById(CARD_NUMBER)).thenReturn(card);

        CreditCard first = service.getCreditCardDetails(CARD_NUMBER);
        CreditCard second = service.getCreditCardDetails(CARD_NUMBER);

        assertSame(first, second);
        verify(ccCrudDao).findCCById(CARD_NUMBER);
    }

    @Test
    void clearCacheForcesNextLookupToCallDaoAgain() {
        when(ccCrudDao.findCCById(CARD_NUMBER)).thenReturn(card());

        service.getCreditCardDetails(CARD_NUMBER);
        service.clearCache();
        service.getCreditCardDetails(CARD_NUMBER);

        verify(ccCrudDao, times(2)).findCCById(CARD_NUMBER);
    }

    private static CreditCard card() {
        CreditCard card = new CreditCard();
        card.setCcNumber(CARD_NUMBER);
        card.setCcType("Visa");
        return card;
    }

    @Configuration
    @EnableCaching
    static class CacheTestConfiguration {

        @Bean
        CCValidateDao ccValidateDao() {
            return mock(CCValidateDao.class);
        }

        @Bean
        CCCrudDao ccCrudDao() {
            return mock(CCCrudDao.class);
        }

        @Bean
        CCService ccService(CCValidateDao validateDao, CCCrudDao crudDao) {
            return new CCServiceImpl(validateDao, crudDao);
        }

        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(
                    "AppServiceCCValidation", "AppServiceCCDetails", "AppServiceCCList");
        }
    }
}
