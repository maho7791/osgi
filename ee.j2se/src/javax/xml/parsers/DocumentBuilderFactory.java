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

package javax.xml.parsers;
public abstract class DocumentBuilderFactory {
	protected DocumentBuilderFactory() { } 
	public abstract java.lang.Object getAttribute(java.lang.String var0);
	public abstract boolean getFeature(java.lang.String var0) throws javax.xml.parsers.ParserConfigurationException;
	public javax.xml.validation.Schema getSchema() { return null; }
	public boolean isCoalescing() { return false; }
	public boolean isExpandEntityReferences() { return false; }
	public boolean isIgnoringComments() { return false; }
	public boolean isIgnoringElementContentWhitespace() { return false; }
	public boolean isNamespaceAware() { return false; }
	public boolean isValidating() { return false; }
	public boolean isXIncludeAware() { return false; }
	public abstract javax.xml.parsers.DocumentBuilder newDocumentBuilder() throws javax.xml.parsers.ParserConfigurationException;
	public static javax.xml.parsers.DocumentBuilderFactory newInstance() { return null; }
	public static javax.xml.parsers.DocumentBuilderFactory newInstance(java.lang.String var0, java.lang.ClassLoader var1) { return null; }
	public abstract void setAttribute(java.lang.String var0, java.lang.Object var1);
	public void setCoalescing(boolean var0) { }
	public void setExpandEntityReferences(boolean var0) { }
	public abstract void setFeature(java.lang.String var0, boolean var1) throws javax.xml.parsers.ParserConfigurationException;
	public void setIgnoringComments(boolean var0) { }
	public void setIgnoringElementContentWhitespace(boolean var0) { }
	public void setNamespaceAware(boolean var0) { }
	public void setSchema(javax.xml.validation.Schema var0) { }
	public void setValidating(boolean var0) { }
	public void setXIncludeAware(boolean var0) { }
}
