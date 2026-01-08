# ContactsSearch (연락처 초성 검색 앱)

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white) 
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)
![Room](https://img.shields.io/badge/Database-Room-DA4437?logo=sqlite&logoColor=white)

안드로이드 기기의 연락처를 읽어와 리스트로 표시하고, **한글 초성 검색** 및 **즐겨찾기 저장** 기능을 제공하는 효율적인 연락처 관리 앱입니다.





## 🚀 주요 기능

- **한글 초성 검색**: "ㄱㅎㄷ" 입력만으로 "김현도"를 찾아내는 강력한 초성 매칭 알고리즘을 제공합니다.
- **즐겨찾기 (Favorite)**: 자주 찾는 연락처를 별도로 등록하고, 앱을 재시작해도 로컬 DB(Room)를 통해 유지됩니다.
- **실시간 리스트 필터링**: 검색어 입력과 동시에 연락처 목록이 즉시 업데이트됩니다.
- **UI/UX 개선**: 즐겨찾기한 연락처는 목록 상단에 별도로 노출되어 접근성을 높였습니다.
- **현대적인 스택**: Jetpack Compose와 Material 3 디자인 가이드를 준수합니다.

## 🛠 Tech Stack

- **언어**: Kotlin
- **UI 프레임워크**: Jetpack Compose (Material 3)
- **아키텍처**: MVVM (Model-View-ViewModel)
- **데이터 저장**: Room Database (Favorites 관리)
- **비구조적 데이터**: Android Contacts ContentProvider (연락처 읽기)
- **반응형 스트림**: Kotlin Coroutines & Flow (`StateFlow`, `combine`)
- **의존성 관리**: Version Catalog (toml) & 수동 DI (AppContainer)

## 📂 프로젝트 구조

```text
app/src/main/java/com/ezlevup/contactssearch/
├── data/
│   ├── local/              # Room DB 관련 (Dao, Entity, Database)
│   ├── model/              # 데이터 모델 (Contact)
│   └── repository/         # 데이터 접근 계층 (ContentProvider & DB 연동)
├── presentation/
│   ├── ui/                 # UI 화면 (Screen, Component)
│   └── viewmodel/          # 비즈니스 로직 및 상태 관리
├── util/                   # 한글 처리 (KoreanUtils) 및 유틸리티
└── MainActivity.kt         # 앱 진입점 및 권한 처리
```

## ⚙️ 실행 방법

1. 이 저장소를 클론합니다.
2. Android Studio (Latest version 권장)에서 프로젝트를 엽니다.
3. 에뮬레이터 또는 실기기를 연결합니다. (안드로이드 7.0 / SDK 24 이상 지원)
4. 앱 실행 후 **연락처 접근 권한**을 허용해 주세요.

## 📝 작업 계획 및 설계 문서

프로젝트의 상세한 히스토리와 설계 내용은 `docs/` 폴더에서 확인하실 수 있습니다.
- [01-연락처-검색-앱-작업-계획서.md](./docs/01-연락처-검색-앱-작업-계획서.md)
- [02-즐겨찾기-기능-구현-작업-계획서.md](./docs/02-즐겨찾기-기능-구현-작업-계획서.md)

---
Developed with ❤️ by Antigravity
