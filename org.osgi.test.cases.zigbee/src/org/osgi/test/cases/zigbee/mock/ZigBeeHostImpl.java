
package org.osgi.test.cases.zigbee.mock;

import java.io.IOException;
import java.math.BigInteger;
import org.osgi.service.zigbee.ZCLCommandHandler;
import org.osgi.service.zigbee.ZCLFrame;
import org.osgi.service.zigbee.ZDPFrame;
import org.osgi.service.zigbee.ZDPHandler;
import org.osgi.service.zigbee.ZigBeeEndpoint;
import org.osgi.service.zigbee.ZigBeeHandler;
import org.osgi.service.zigbee.ZigBeeHost;
import org.osgi.service.zigbee.descriptors.ZigBeeNodeDescriptor;
import org.osgi.service.zigbee.descriptors.ZigBeePowerDescriptor;

/**
 * Mocked impl.
 * 
 * @author $Id: 41e7051fd4fa89a209de2a9cc136c59da4d7c649 $
 */
public class ZigBeeHostImpl extends ZigBeeNodeImpl implements ZigBeeHost {

	private int	channelAsInt;
	private int	securityLevel;

	/**
	 * @param hostPId
	 * @param panId
	 * @param channel
	 * @param baud
	 * @param securityLevel
	 * @param IEEEAddress
	 * @param endpoints
	 */
	public ZigBeeHostImpl(String hostPId, int panId, int channel,
			int securityLevel, BigInteger IEEEAddress,
			ZigBeeEndpoint[] endpoints) {
		super(IEEEAddress, hostPId, endpoints);
		this.channelAsInt = channel;
		this.securityLevel = securityLevel;
	}

	public ZigBeeHostImpl(String hostPId, int panId, int channel,
			int securityLevel, BigInteger IEEEAddress,
			ZigBeeEndpoint[] endpoints, ZigBeeNodeDescriptor nodeDesc,
			ZigBeePowerDescriptor powerDesc, String userdescription) {
		super(IEEEAddress,
				hostPId,
				endpoints,
				nodeDesc,
				powerDesc,
				userdescription);
		this.channelAsInt = channel;
		this.securityLevel = securityLevel;
	}

	public void start() throws Exception {

	}

	public void stop() throws Exception {

	}

	public boolean isStarted() {

		return false;
	}

	public void setPanId(int panId) {

	}

	public void setExtendedPanId(long extendedPanId) {

	}

	public String getPreconfiguredLinkKey() throws Exception {

		return null;
	}

	public void refreshNetwork(ZigBeeHandler handler) throws Exception {

	}

	public void permitJoin(short duration) throws Exception {

	}

	public int getChannelMask() throws Exception {

		return 0;
	}

	public int getChannel() {
		return channelAsInt;
	}

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setLogicalType(short logicalNodeType) throws Exception {

	}

	public void setChannelMask(int mask) throws IOException,
			IllegalStateException {

	}

	public void createGroupService(int groupAddress) throws Exception {

	}

	public void removeGroupService(int groupAddress) throws Exception {

	}

	public void broadcast(int clusterID, ZCLFrame frame,
			ZCLCommandHandler handler) {

	}

	public void broadcast(int clusterID, ZCLFrame frame,
			ZCLCommandHandler handler, String exportedServicePID) {

	}

	public void updateNetworkChannel(byte channel)
			throws IllegalStateException, IOException {

	}

	public short getBroadcastRadius() {

		return 0;
	}

	public void setBroadcastRadius(short broadcastRadius)
			throws IllegalArgumentException, IllegalStateException {

	}

	public BigInteger getIEEEAddress() {

		return IEEEAddress;
	}

	public int getNetworkAddress() {

		return 0;
	}

	public String getHostPid() {

		return hostPId;
	}

	public int getPanId() {

		return 0;
	}

	public BigInteger getExtendedPanId() {

		return null;
	}

	public void getLinksQuality(ZigBeeHandler handler) {

	}

	public void getRoutingTable(ZigBeeHandler handler) {

	}

	public void leave(ZigBeeHandler handler) {

	}

	public void leave(boolean rejoin, boolean removeChildren,
			ZigBeeHandler handler) {

	}

	public void invoke(int clusterIdReq, int expectedClusterIdRsp,
			ZDPFrame message, ZDPHandler handler) {

	}

	public void invoke(int clusterIdReq, ZDPFrame message, ZDPHandler handler) {

	}
}