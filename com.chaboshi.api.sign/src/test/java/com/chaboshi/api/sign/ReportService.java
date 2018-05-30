/**
 * 
 */

package com.chaboshi.api.sign;

import com.chaboshi.util.CBS;

/**
 * @author lee
 *
 */
public class ReportService {
  private static final String userid = "104";
  private static final String keySecret = "cdb7cee9495f159710cc6280528c3868";
  private static final String vin = "LSVET69F1B2709452";
  private static CBS cbs = CBS.getInstance(userid, keySecret);

  public static void getReport() {
    String report = cbs.getBuyReport(vin, null, null, null);
    System.out.println(report);
  }
}
