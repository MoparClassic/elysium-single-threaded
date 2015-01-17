package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ReportMessage extends AbstractMessage {

    private final byte reasonCode;
    private final long reportedHash;

    public ReportMessage(long reportedHash, byte reasonCode) {
        this.reportedHash = reportedHash;
        this.reasonCode = reasonCode;
    }

    public byte getReasonCode() {
        return reasonCode;
    }

    public long getReportedHash() {
        return reportedHash;
    }
}
