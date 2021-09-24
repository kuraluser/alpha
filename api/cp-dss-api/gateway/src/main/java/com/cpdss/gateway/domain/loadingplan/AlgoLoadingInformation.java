/**
 * 
 */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;

import lombok.Data;

/**
 * @author pranav.k
 *
 */

@Data
public class AlgoLoadingInformation {

	private LoadingRates loadingRates;
	private List<BerthDetails> berthDetails;
	private LoadingSequences loadingSequences;
}
