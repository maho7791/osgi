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

package javax.swing;
public class JToggleButton extends javax.swing.AbstractButton implements javax.accessibility.Accessible {
	protected class AccessibleJToggleButton extends javax.swing.AbstractButton.AccessibleAbstractButton implements java.awt.event.ItemListener {
		public AccessibleJToggleButton() { } 
		public void itemStateChanged(java.awt.event.ItemEvent var0) { }
	}
	public static class ToggleButtonModel extends javax.swing.DefaultButtonModel {
		public ToggleButtonModel() { } 
	}
	public JToggleButton() { } 
	public JToggleButton(java.lang.String var0) { } 
	public JToggleButton(java.lang.String var0, javax.swing.Icon var1) { } 
	public JToggleButton(java.lang.String var0, javax.swing.Icon var1, boolean var2) { } 
	public JToggleButton(java.lang.String var0, boolean var1) { } 
	public JToggleButton(javax.swing.Action var0) { } 
	public JToggleButton(javax.swing.Icon var0) { } 
	public JToggleButton(javax.swing.Icon var0, boolean var1) { } 
}
