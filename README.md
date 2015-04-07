# JacksonDatabindDemo
A simple app demonstrating how to use the Jackson Databind library rather than doing all of the parsing yourself.

This recipe shows how to use the [Jackson Databind][jackson-databind] library in an Android app to parse a JSON response from a server. I [previously][jsondemoapp] showed how you can use Jackson to manually parse your JSON, but there's an even easier way.

[![Build Status](https://img.shields.io/travis/neilmcguiggan/jackson_databind_demo.svg)](https://travis-ci.org/neilmcguiggan/jackson_databind_demo)

## Add Dependencies

The first step in using Jackson is to import the libraries from Maven Central. I'm using the [core][jackson-core] library, the [databind][jackson-databind] library for binding JSON to Java objects, and the [annotations][jackson-annotations] library containing the core annotations for Jackson.

I'm using [OkHttp][okhttp] for networking, so include that too if you want to follow this tutorial exactly.

```
//Jackson libraries for JSON parsing
compile 'com.fasterxml.jackson.core:jackson-core:2.5.1'

//okhttp client for network requests
compile 'com.squareup.okhttp:okhttp:2.3.0'
compile 'com.fasterxml.jackson.core:jackson-databind:2.5.1'
compile 'com.fasterxml.jackson.core:jackson-annotations:2.5.1'
```

## Make The Request

From whichever class requires the data, make an asynchronous request to the Data Manager class, in this case `UserManager`:

```
UserManager.getInstance().requestUsers(this);
```

The parameter `this` is a `UserManagerListener`, used for receiving callbacks when the data is loaded. This is a pretty common pattern in Android, even referenced in [Google's own tutorials][callbacks]. It lets you make the request asynchronously, meaning you can get on with other things while the data loads. Here's the interface:

```
/**
 * Interface to be implemented by any class that requests data from the UserManager.
 */
public interface UserManagerListener {
    /**
     * Callback for when the social data has been fetched.
     *
     * @param userList The list of users that has been loaded.
     */
    void onUserDataLoaded(List<User> userList);
}
```

And this is my implementation in the `MainActivity` class:

```
@Override
public void onUserDataLoaded(List<User> userList) {
    //the userList is loaded, do something with it.
}
```

## The Model Classes

Create a POJO for each model object that you want to map to a JSON object. In this project these are `User` and `Friend`.

Each property in the class will be mapped to a JSON property:

```
private String mId;

@JsonProperty("_id")
public String getId() {
    return mId;
}

public void setId(String id) {
    mId = id;
}
```

The `@JsonProperty` annotation can be placed next to the field declaration, the getter or the setter. If your field name and the JSON field name are the same, you don't need the annotation as it'll be derived by Jackson.

If you don't want to use every value in the JSON, use need to annotate the class with `@JsonIgnoreProperties(ignoreUnknown = true)`. This tells the JSON parser to ignore everything that you don't specifically ask to be mapped.

## The Data Manager Class

If you've used OkHttp before, the `requestUsers` method should be really easy to understand. Thankfully, even if you've not used OkHttp before, it should be easy to understand as it's a really clear API.

The interesting part in this tutorial is here, in the response callback:

```
final byte[] responseBytes = response.body().bytes();
ObjectMapper objectMapper = new ObjectMapper();
User[] userList = objectMapper.readValue(responseBytes, User[].class);
listener.onUserDataLoaded(Arrays.asList(userList));
```

All we have to do is pass the bytes of the response, or even simply a String version of the response, to the `ObjectMapper` instance and it will map it to an instance of the `Class` that you pass in as the second parameter. In this case, it's an array rather than a single object, but you can pass arrays in too.

And that's it! We now have a fully parsed array of users, built from the JSON that was received from the server.

[jsondemoapp]: https://github.com/neilmcguiggan/JSONDemoApp "GitHub: neilmcguiggan/JSONDemoApp"
[jackson]: https://github.com/FasterXML/jackson "GitHub: FasterXML/jackson"
[jackson-core]: https://github.com/FasterXML/jackson-core "GitHub: FasterXML/jackson-core"
[jackson-databind]: https://github.com/FasterXML/jackson-databind "GitHub: FasterXML/jackson-databind"
[jackson-annotations]: https://github.com/FasterXML/jackson-annotations "GitHub: FasterXML/jackson-annotations"
[okhttp]: http://square.github.io/okhttp/ "OkHttp"
[callbacks]: http://developer.android.com/training/basics/fragments/communicating.html "Communicating with Other Fragments"
[jsonparser]: https://github.com/FasterXML/jackson-core/blob/master/src/main/java/com/fasterxml/jackson/core/JsonParser.java "JsonParser.java"
[jsontokens]: https://github.com/FasterXML/jackson-core/blob/master/src/main/java/com/fasterxml/jackson/core/JsonToken.java "JsonToken.java"
