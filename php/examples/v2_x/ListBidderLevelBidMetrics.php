<?php
/**
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

require_once __DIR__ . '/../../BaseExample.php';

/**
 * This example illustrates how to retrieve Bidder-level Bid Metrics.
 *
 * Additional RTB Troubleshooting resources that can be similarly used to
 * retrieve metrics for real-time bidding can be found nested under the
 * bidders.filterSets resource.
 */
class ListBidderLevelBidMetrics extends BaseExample {

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'bidderResourceId',
            'display' => 'Bidder Resource ID',
            'required' => true
        ],
        [
            'name' => 'filterSetResourceId',
            'display' => 'Filter Set Resource ID',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;
    $filterSetName = sprintf(
        'bidders/%s/filterSets/%s',
        $values['bidderResourceId'],
        $values['filterSetResourceId']
    );
    $result = $this->service->bidders_filterSets_bidMetrics
        ->listBiddersFilterSetsBidMetrics($filterSetName);
    print sprintf(
        '<h2>Listing Bidder-level Bid Metrics for filterSet "%s"</h2>',
        $filterSetName
    );
    foreach ($result['bidMetricsRows'] as $bidMetricsRow) {
      $this->printResult($bidMetricsRow);
    }
  }

  /**
   * @see BaseExample::getClientType()
   */
  public function getClientType() {
    return ClientType::AdExchangeBuyerII;
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'RTB Troubleshooting: List Bidder-level Bid Metrics';
  }
}

