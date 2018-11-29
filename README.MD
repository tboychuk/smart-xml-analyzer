# Smart XML Analyzer

`SmartXMLAnalyzer` provides a static API that allows to **analyze an element by id from one HTML** and **find its location in
another HTML** file even in case element **id has been changed or removed and the element location in DOM tree is different.**

 
### How to build
* maven command `clean package` - will produce an executable jar

### How to run
* `java -jar sxa.jar origin-file-path diff-base-file-path target-element-id`

E.g. suppose you've got an executable file [`sxa.jar`](https://drive.google.com/uc?export=download&id=10Sh5aAX_EA3C47UYerJiF9Er6DZH2LRP), which is located in directory `test-sxa`. This folder also contains some
HTML file samples collected in directory [`samples`](https://drive.google.com/uc?export=download&id=1n0qtsVpVHq5KDI2f-CgAfW-a2tVw74lj). 
* open terminal and go to directory `test-sxa`
* run `java -jar sxa.jar ./samples/sample-0-origin.html ./samples/sample-1-evil-gemini.html make-everything-ok-button`

The result is the path to the target element in diff case file. It will be printed to the console, like it the following 
example: 
* `html > body > div > div > div[3] > div[1] > div > div[2] > a[2]`
