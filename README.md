TODO

-websocket interface
-binary data
-100,000Items
-Unimplemented methods
-timed cloud entries
-data compression

SystemFree   =1,095
com.goldennode.client.GoldenNodeRuntimeException: com.goldennode.client.GoldenNodeException: java.net.SocketException: Unrecognized Windows Sockets error: 0: recv failed
	at com.goldennode.client.GoldenNodeMap.putAll(GoldenNodeMap.java:83)
	at com.goldennode.client.HybridMapImpl.putAll(HybridMapImpl.java:135)
	at com.goldennode.client.TestHybridMap$1.run(TestHybridMap.java:36)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.goldennode.client.GoldenNodeException: java.net.SocketException: Unrecognized Windows Sockets error: 0: recv failed
	at com.goldennode.client.RestClient.call(RestClient.java:97)
	at com.goldennode.client.RestClient.call(RestClient.java:102)
	at com.goldennode.client.service.MapServiceImpl.putAll(MapServiceImpl.java:57)
	at com.goldennode.client.GoldenNodeMap.putAll(GoldenNodeMap.java:81)
	... 3 more
Caused by: java.net.SocketException: Unrecognized Windows Sockets error: 0: recv failed
	at java.net.SocketInputStream.socketRead0(Native Method)
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
	at java.net.SocketInputStream.read(SocketInputStream.java:171)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at sun.security.ssl.InputRecord.readFully(InputRecord.java:465)
	at sun.security.ssl.InputRecord.read(InputRecord.java:503)
	at sun.security.ssl.SSLSocketImpl.readRecord(SSLSocketImpl.java:990)
	at sun.security.ssl.SSLSocketImpl.performInitialHandshake(SSLSocketImpl.java:1388)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1416)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1400)
	at sun.net.www.protocol.https.HttpsClient.afterConnect(HttpsClient.java:559)
	at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(AbstractDelegateHttpsURLConnection.java:185)
	at sun.net.www.protocol.http.HttpURLConnection.getOutputStream0(HttpURLConnection.java:1340)
	at sun.net.www.protocol.http.HttpURLConnection.getOutputStream(HttpURLConnection.java:1315)
	at sun.net.www.protocol.https.HttpsURLConnectionImpl.getOutputStream(HttpsURLConnectionImpl.java:264)
	at com.goldennode.client.RestClient.call(RestClient.java:81)
	... 6 more

