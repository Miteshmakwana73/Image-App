# Image-App

**Android Assignment**


**Overview**:
Develop an application for Android to efficiently load and display images in a scrollable grid. You are not allowed to use any third-party image loading library.

**Tasks**:
Image Grid: Show a 3-column square image grid. The images should be center-cropped.

Image Loading:

Implement asynchronous image loading using this (https://acharyaprashant.org/api/v2/content/misc/media-coverages?limit=100) url.

In the response of above API you will get thumbnail object in each array element. Use following formula to construct Image URL using fields of thumbnail object

imageURL = domain + "/" + basePath + "/0/" + key
Display: User should be able to scroll through at least 100 images.

Caching:  Develop a caching mechanism to store images retrieved from the API in both memory and disk cache for efficient retrieval.

Error Handling: Handle network errors and image loading failures gracefully when fetching images from the API, providing informative error messages or placeholders for failed image loads.

Implementation to be done in Kotlin or Java using Native technology.


**Evaluation criteria**
Images should load lazily. Also if you are on for example page 1 and quickly scroll to page 10, then the image loading of page 1 should be cancelled, and it should start loading for page 10.

There must be absolutely no lag while scrolling the image grid.

Disk and Memory cache both should work. Disk cache should be used If image is missing in memory cache. When image is read from disk, memory cache should be updated.

If your code does not compile with the latest Android Studio, your assignment will be rejected without checking.

Step by step and clear instructions should be present in the README file to run the code if any.

Assignment submitted using mediocre technologies like Flutter, React Native etc. will be rejected.


**Mode of submission**
Push it on GitHub and share the link with us.


**Submission deadline**
Within 7 days from receiving the assignment.

I have used Android Giraffe and compileSdk is 34.