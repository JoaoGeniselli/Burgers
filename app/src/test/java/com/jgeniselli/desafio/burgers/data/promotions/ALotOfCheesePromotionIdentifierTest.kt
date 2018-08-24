package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Ingredient
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times

class ALotOfCheesePromotionIdentifierTest {

    private val cheeseId = 5

    @Test
    fun ignoreWhenReceiveLessThan3OfCheese() {
        val burger = mockBurger(0)
        val identifier = ALotOfCheesePromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(0)).addPromotion(Promotion.makeALotOfCheese(3))
    }

    @Test
    fun addPromotionWhenReceive3OfCheese() {
        val burger = mockBurger(3)
        val identifier = ALotOfCheesePromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfCheese(3))
    }

    @Test
    fun addPromotionWhenReceiveMoreThan3OfCheese() {
        val burger = mockBurger(6)
        val identifier = ALotOfCheesePromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfCheese(6))
    }

    @Test
    fun addThanRemovePromotionOnConditionChanged() {
        val burger = mockBurger(6)
        val identifier = ALotOfCheesePromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        mockAmount(burger, 1)
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfCheese(6))
        verify(burger, Times(1)).removePromotion(Promotion.makeALotOfCheese(6))
    }

    private fun mockBurger(cheeseAmount: Int): Burger {
        val mock = mock(Burger::class.java)
        mockAmount(mock, cheeseAmount)
        return mock
    }

    private fun mockAmount(mock: Burger, cheeseAmount: Int) {
        `when`(mock.getAmount(Ingredient.makeForPrice(cheeseId, 0.0, "")))
                .thenReturn(cheeseAmount)
    }
}