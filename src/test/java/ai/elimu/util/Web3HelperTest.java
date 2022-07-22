package ai.elimu.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class Web3HelperTest  {

    @Test
    public void isSignatureValidTest() {
        assertTrue(Web3Helper.isSignatureValid("0x13c0dabeb938c21524c59cdc40bcb6fdb3618754", "0xafec8e1d70daaf0e45149d26bd05ed81f96c28f2187d2ae870387e33927f984220fdf638c4f5cafc9a0f529867abb5de2e92be0bd25fc3c8c5e33a612bf703371c", "Test"));
        assertTrue(Web3Helper.isSignatureValid("0x19F00CD00fEe14f9FADc378702379a7CBc04a7c1", "0x020b04d5b11b2df3b1d61fb21663b853a9fb638b87acca1025b6eca92a7287c967909f2cb9382c6c491a941c43c94356a5bae5c39702c6dc9c9e28c1de34f2a801", "XXX"));
        assertTrue(Web3Helper.isSignatureValid("0x2b36e665af634018f9eaec1d249633ae2a9ad825", "0xaa1eadb925d1e5236182f80bdc8c707f2fda1844f03268473508a519ef844359115ac56ea510e07712a96c4dccc34432b1c583285d43cd369bc26eba827267941b", "TestMsg"));
        assertTrue(Web3Helper.isSignatureValid("0xd26e1be34cd621c7ed436135748c98599ed44ee6", "0xde8fe6016d4d1a5fa7df42e158d102ab1ec69aabbb854ebe09ca95215a88b4025da96d4814140d2af2aa1a3d4146cd3ea16592f3f9e83e6d3dc5839f94e044001c", "SJ!O@(J@9j"));
    }
}