

BrandieR is a **remake** of official [Brandie Android app](https://play.google.com/store/apps/details?id=io.brandie.brandie).

# BrandieR 📱 (work-in-progress 👷🔧️👷‍♀️⛏)

## Android development

BrandieR is an app which attempts to use the latest cutting edge libraries and tools to provide the best user experience. As a summary:

 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) and [RxJava](https://github.com/ReactiveX/RxJava)
 * Uses many of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): ViewModel, Lifecycle, Navigation
 * Uses [Hilt](https://dagger.dev/hilt/) for dependency injection
 * Uses [MVVM architecture](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
 * Unit testing
 * UI testing - coming soon

### Code style

This project uses [ktlint](https://github.com/pinterest/ktlint). Run `gradlew ktlintDebugCheck` on windows 💻 to format the code.

### API keys

You need to supply API / client keys for the various services the
app uses:

- Get your api key from [Pixabay](https://pixabay.com/api/docs/)

You can find information about how to gain access via the relevant links.

Once you obtain the keys, you can set them in your [gradle.properties](/gradle.properties) :

```
# Get these from pixabay.com
ApiKey=<insert>
```
