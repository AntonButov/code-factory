# Bridge
The module which response which AI models use.

Context - all code which need AI for implement you interface.
Interface - interface which had to implement by AI.

## Api

suspend fun getCode(context: String, interface: String): String

We get the bridge: context and interface. The bridge returns a code.

Bridge has context history end give one to AI if need.