/*
 * Copyright (c) OSGi Alliance (2001, 2013). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.xml.crypto.dsig;
public interface SignatureMethod extends javax.xml.crypto.AlgorithmMethod, javax.xml.crypto.XMLStructure {
	public final static java.lang.String DSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
	public final static java.lang.String HMAC_SHA1 = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
	public final static java.lang.String RSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
}
