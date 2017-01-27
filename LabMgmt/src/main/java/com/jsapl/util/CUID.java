/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsapl.util;;


public final class CUID {
	
	private static final long NODE_ID = 1;
	
	private static CUID cuid = new CUID();
	private short nextId = 1;
	
	public static CUID getInstance() {
		return cuid;
	}
	
	public synchronized Long nextId() {
		// Push the Node_ID to max bits
		long maxBitNodeId = NODE_ID << (Long.numberOfLeadingZeros(NODE_ID) - 1);
		
		int nodeIdZeroBitSize = Long.numberOfTrailingZeros(maxBitNodeId);
		
		// Create timestamp and shift the bits next to Node ID
		long currentTime = System.currentTimeMillis();
		long maxBitTimestamp = currentTime << (Long.numberOfLeadingZeros(currentTime) - (64 - nodeIdZeroBitSize));
		
		// Because timestamp can give only millisecond precision add a incremental Id.
		long uniqueClusterId;
		if (nextId < Short.MAX_VALUE) {
			uniqueClusterId = maxBitNodeId | maxBitTimestamp | nextId++;
		} else {
			// Counter Reset after reaching the SHORT max value.
			nextId = 1;
			uniqueClusterId = maxBitNodeId | maxBitTimestamp | nextId++;
		}
		
		return uniqueClusterId;
	}
	
}