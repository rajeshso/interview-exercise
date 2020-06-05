# Developer Programming Exercise

## User Story

As a trader I want to be able to monitor stock prices such that when they breach a trigger level orders can be executed automatically.

## Exercise

Given the following interface definitions (provided)

```
public interface ExecutionService {
    void buy(String security, double price, int volume);
    void sell(String security, double price, int volume);
}
```

```
public interface PriceListener {
    void priceUpdate(String security, double price);
}
```

```
public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
}
```

Develop a basic implementation of the PriceListener interface that provides the following behaviour:

1. Connects to a PriceSource instance
1. Monitors price movements on a specified single stock (e.g. "IBM")
1. Executes a single "buy" instruction for a specified number of lots (e.g. 100) as soon as the price of that stock is seen to be below
a specified price (e.g. 55.0). Don’t worry what units that is in.

### Considerations

* Please "work out loud" and ask questions
* This is not a test of your API knowledge so feel free to check the web as reference
* There is no specific solution we are looking for

### Some libraries already available:

* Java 8
* JUnit 4
* Mockito
* EasyMock
* JMock

### Interview Excercise Solution - Readme

#### Structure 

The Listener monitors a firehose of events from the Price Source. The Price Source is an async process and it required some additional state. Hence, the SourceImpl has some state variables.

The Trading Strategy is the main program.

The PriceSource and its implementation are async process that notifies events to the interested listeners. The rate and the security are random, but within a range. Hence, every run will have unique set of trade prices.

The Price Listener monitors the events, strikes a trade when there is price matching a given critieria

The TradeExceutionService prints the trade that is successful.

The PriceSource extends Runnable to make it an async

The objective of the candidate is to keep the implementation very simple and easy to read. So, the functionalities are minimal. 

The rest of the program is self-explanatory.

Note: Lombok, an annotation processor is added for brevity.
In Intellij, please enable Annotation processing at Preferences->Build,Execution, Deployment-> Compiler-> Annotation processors -> Enable
