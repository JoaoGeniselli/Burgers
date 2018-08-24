package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Ingredient
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times

class ALotOfMeatPromotionIdentifierTest {

    private val cheeseId = 3

    @Test
    fun ignoreWhenReceiveLessThan3OfMeat() {
        val burger = mockBurger(0)
        val identifier = ALotOfMeatPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(0)).addPromotion(Promotion.makeALotOfMeat(3))
    }

    @Test
    fun addPromotionWhenReceive3OfMeat() {
        val burger = mockBurger(3)
        val identifier = ALotOfMeatPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfMeat(3))
    }

    @Test
    fun addPromotionWhenReceiveMoreThan3OfMeat() {
        val burger = mockBurger(6)
        val identifier = ALotOfMeatPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfMeat(6))
    }

    @Test
    fun addThanRemovePromotionOnConditionChanged() {
        val burger = mockBurger(6)
        val identifier = ALotOfMeatPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        mockAmount(burger, 1)
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeALotOfMeat(6))
        verify(burger, Times(1)).removePromotion(Promotion.makeALotOfMeat(6))
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