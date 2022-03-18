# Photon Decoder

This JAVA library is a port from a library made with Go. The link for the original lib is [here](https://github.com/broderickhyman/photon_spectator).
My library is not completely equal to that because I've renamed some classes to be more similar to the ENet library made with C, which you can see [here](http://enet.bespin.org/index.html).
The main usage is to decode network packets used by any Photon server and retrieve game data. 
If you need some help or talk about it, feel free to email me.

## How to use

To understand more about the Photon protocol, click on this [link](https://doc.photonengine.com/en-us/realtime/current/reference/binary-protocol).
With that understanding, you will need some network sniffer library, like [Pcap4J](https://github.com/kaitoy/pcap4j) to get the UDP/TCP payload.
After getting the payload, follow the steps below to get the game data.

* Start with `ENetPacket.decode` method to get the packet.
* Get commands with `enetPacket.decodeCommands().getCommands()`.
* Get the `PhotonOperationDecoder` instance with `PhotonOperationDecoder.getInstance()`
* Use the decoder to decode every command with `decoder.decode`. Because I didn't decode every command type or the command can be a message fragment, the method will return an Optional value.
* Finally, when you get some message, you can decode it to a `Parameter` using `Parameter.decode`.

Full example below
```java

```

# Useful links

* https://doc.photonengine.com/en-us/realtime/current/reference/binary-protocol
* https://github.com/broderickhyman/photon_spectator
* http://enet.bespin.org/index.html