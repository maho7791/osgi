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

package javax.swing.plaf.basic;
public class BasicDirectoryModel extends javax.swing.AbstractListModel<java.lang.Object> implements java.beans.PropertyChangeListener {
	public BasicDirectoryModel(javax.swing.JFileChooser var0) { } 
	public void addPropertyChangeListener(java.beans.PropertyChangeListener var0) { }
	public boolean contains(java.lang.Object var0) { return false; }
	public void fireContentsChanged() { }
	protected void firePropertyChange(java.lang.String var0, java.lang.Object var1, java.lang.Object var2) { }
	public java.util.Vector<java.io.File> getDirectories() { return null; }
	public java.lang.Object getElementAt(int var0) { return null; }
	public java.util.Vector<java.io.File> getFiles() { return null; }
	public java.beans.PropertyChangeListener[] getPropertyChangeListeners() { return null; }
	public int getSize() { return 0; }
	public int indexOf(java.lang.Object var0) { return 0; }
	public void intervalAdded(javax.swing.event.ListDataEvent var0) { }
	public void intervalRemoved(javax.swing.event.ListDataEvent var0) { }
	public void invalidateFileCache() { }
	protected boolean lt(java.io.File var0, java.io.File var1) { return false; }
	public void propertyChange(java.beans.PropertyChangeEvent var0) { }
	public void removePropertyChangeListener(java.beans.PropertyChangeListener var0) { }
	public boolean renameFile(java.io.File var0, java.io.File var1) { return false; }
	protected void sort(java.util.Vector<? extends java.io.File> var0) { }
	public void validateFileCache() { }
}
