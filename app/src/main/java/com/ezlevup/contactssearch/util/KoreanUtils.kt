package com.ezlevup.contactssearch.util

/**
 * 한글 초성 처리 유틸리티
 *
 * 한글 문자열을 초성으로 변환하고, 초성 검색을 지원합니다.
 */
object KoreanUtils {

    // 한글 초성 배열
    private val CHOSUNG_LIST = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    // 한글 유니코드 시작 값
    private const val HANGUL_BEGIN_UNICODE = 0xAC00 // '가'
    private const val HANGUL_END_UNICODE = 0xD7A3   // '힣'

    // 초성 개수 (19개)
    private const val CHOSUNG_COUNT = 19
    // 중성 개수 (21개)
    private const val JUNGSUNG_COUNT = 21
    // 종성 개수 (28개, 받침 없음 포함)
    private const val JONGSUNG_COUNT = 28

    /**
     * 한글 문자열을 초성으로 변환합니다
     *
     * @param text 변환할 문자열
     * @return 초성 문자열
     *
     * 예시:
     * - "김현도" → "ㄱㅎㄷ"
     * - "이영희" → "ㅇㅇㅎ"
     * - "abc" → "abc" (한글이 아닌 문자는 그대로 반환)
     */
    fun getChosung(text: String): String {
        val result = StringBuilder()

        for (char in text) {
            if (char.code in HANGUL_BEGIN_UNICODE..HANGUL_END_UNICODE) {
                // 한글인 경우 초성 추출
                val unicode = char.code - HANGUL_BEGIN_UNICODE
                val chosungIndex = unicode / (JUNGSUNG_COUNT * JONGSUNG_COUNT)
                result.append(CHOSUNG_LIST[chosungIndex])
            } else {
                // 한글이 아닌 경우 그대로 추가
                result.append(char)
            }
        }

        return result.toString()
    }

    /**
     * 이름이 초성 검색어와 일치하는지 확인합니다
     *
     * @param name 검색 대상 이름
     * @param query 검색어 (초성 또는 일반 텍스트)
     * @return 일치 여부
     *
     * 검색 방식:
     * 1. 검색어가 초성인 경우: 이름의 초성과 비교 (부분 일치 지원)
     * 2. 검색어가 일반 텍스트인 경우: 이름에 포함 여부 확인
     *
     * 예시:
     * - matchesChosung("김현도", "ㄱㅎㄷ") → true (완전 일치)
     * - matchesChosung("김현도", "ㄱㅎ") → true (부분 일치)
     * - matchesChosung("김현도", "현도") → true (일반 텍스트)
     * - matchesChosung("김현도", "ㄴㅎ") → false
     */
    fun matchesChosung(name: String, query: String): Boolean {
        if (query.isEmpty()) return true
        if (name.isEmpty()) return false

        // 검색어가 초성인지 확인
        val isChosungQuery = query.all { it in CHOSUNG_LIST }

        return if (isChosungQuery) {
            // 초성 검색: 이름의 초성을 추출하여 비교
            val nameChosung = getChosung(name)
            nameChosung.startsWith(query, ignoreCase = true)
        } else {
            // 일반 텍스트 검색: 이름에 포함 여부 확인
            name.contains(query, ignoreCase = true)
        }
    }

    /**
     * 문자가 한글 초성인지 확인합니다
     *
     * @param char 확인할 문자
     * @return 초성 여부
     */
    fun isChosung(char: Char): Boolean {
        return char in CHOSUNG_LIST
    }
}
