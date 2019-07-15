# finalProject
QA automation final project
##### Testing of YouTube site.

##### The source code includes two folders:
- **myFunctions** - all functions I wrote, to use in the tests. A separate class for each subject.
- **myTests** - all tests (sanity, functional and error). A separate class for each type of tests, plus the common "father" class.

*Each class contains its description at the beginning of the code.*
*Every line of the code is explained in the comments.*
*Every action is checked for successful execution before proceeding further.*

##### Browsers:
The code tests the site in one of two browsers to choose from: Chrome and Firefox.
User can choose the browser at the begining of each type of tests.


##### There are three types of tests:
- **Sanity tests:**
1. Finds and playing a video
- **Functional tests:**
1. Checks successful change of site language.
2. Checks successful subscription to a channel.
3. Checks successful addition of a video to the "watch later" list.
4. Checks successful transition to the next video.
- **Error handling tests:**
1. Verifies error due to invalid video uploading.
2. Verifies error due to creating a new brand account with an invalid name.
3. Verifies error due to adding an invalid Google-Analytics-ID. 
4. Verifies error due to saving a watermark without a file.
