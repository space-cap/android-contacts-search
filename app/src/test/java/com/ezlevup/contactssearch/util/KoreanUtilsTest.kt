package com.ezlevup.contactssearch.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** KoreanUtils 단위 테스트 */
class KoreanUtilsTest {

    @Test
    fun `getChosung_이름을_초성으로_정확히_변환한다`() {
        assertEquals("ㄱㅎㄷ", KoreanUtils.getChosung("김현도"))
        assertEquals("ㅇㅇㅎ", KoreanUtils.getChosung("이영희"))
        assertEquals("ㅂㅁㅅ", KoreanUtils.getChosung("박민수"))
        assertEquals("ㄷㄹㅁ", KoreanUtils.getChosung("도라마"))
    }

    @Test
    fun `getChosung_한글이_아닌_문자는_그대로_유지한다`() {
        assertEquals("abc", KoreanUtils.getChosung("abc"))
        assertEquals("ㄱㄴㄷ 123", KoreanUtils.getChosung("가나다 123"))
    }

    @Test
    fun `matchesChosung_초성_완전_일치_테스트`() {
        assertTrue(KoreanUtils.matchesChosung("김현도", "ㄱㅎㄷ"))
        assertTrue(KoreanUtils.matchesChosung("이영희", "ㅇㅇㅎ"))
    }

    @Test
    fun `matchesChosung_초성_부분_일치_테스트`() {
        assertTrue(KoreanUtils.matchesChosung("김현도", "ㄱ"))
        assertTrue(KoreanUtils.matchesChosung("김현도", "ㄱㅎ"))
        assertFalse(KoreanUtils.matchesChosung("김현도", "ㅎㄷ")) // 현재 구현은 startsWith 기반
    }

    @Test
    fun `matchesChosung_일반_텍스트_검색_테스트`() {
        assertTrue(KoreanUtils.matchesChosung("김현도", "현도"))
        assertTrue(KoreanUtils.matchesChosung("김현도", "김"))
        assertFalse(KoreanUtils.matchesChosung("김현도", "철수"))
    }

    @Test
    fun `matchesChosung_빈_검색어는_항상_참을_반환한다`() {
        assertTrue(KoreanUtils.matchesChosung("김현도", ""))
    }
}
