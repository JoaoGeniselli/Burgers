package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.MenuBurger
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.internal.verification.Times

class PromotionIdentifierListenerTest {

    @Test
    fun notifyOneIdentifier() {
        val identifiers = mockIdentifiers(1)
        val listener = PromotionIdentifierListener(identifiers)
        val burger = mock(MenuBurger::class.java)
        listener.onIngredientsChanged(burger)
        verify(identifiers.first(), Times(1)).applyPromotionIfAvailable(burger)
    }

    @Test
    fun notifyFewIdentifiers() {
        val identifiers = mockIdentifiers(2)
        val listener = PromotionIdentifierListener(identifiers)
        val burger = mock(MenuBurger::class.java)
        listener.onIngredientsChanged(burger)
        verify(identifiers.first(), Times(1)).applyPromotionIfAvailable(burger)
        verify(identifiers.last(), Times(1)).applyPromotionIfAvailable(burger)
    }

    private fun mockIdentifiers(amount: Int): List<PromotionIdentifier> {
        val identifiers = ArrayList<PromotionIdentifier>()
        for (i in 0 .. amount) {
            identifiers.add(mock(PromotionIdentifier::class.java))
        }
        return identifiers
    }
}