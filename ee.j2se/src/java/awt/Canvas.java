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

package java.awt;
public class Canvas extends java.awt.Component implements javax.accessibility.Accessible {
	protected class AccessibleAWTCanvas extends java.awt.Component.AccessibleAWTComponent {
		protected AccessibleAWTCanvas() { } 
	}
	public Canvas() { } 
	public Canvas(java.awt.GraphicsConfiguration var0) { } 
	public void createBufferStrategy(int var0) { }
	public void createBufferStrategy(int var0, java.awt.BufferCapabilities var1) throws java.awt.AWTException { }
	public java.awt.image.BufferStrategy getBufferStrategy() { return null; }
}
