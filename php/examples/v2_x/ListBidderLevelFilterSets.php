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
 * This example illustrates how to retrieve all Bidder-level Filter Sets.
 */
class ListBidderLevelFilterSets extends BaseExample {

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'bidderResourceId',
            'display' => 'Bidder Resource ID',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;
    $ownerName = sprintf(
        'bidders/%s',
        $values['bidderResourceId']
    );
    $result = $this->service->bidders_filterSets
        ->listBiddersFilterSets($ownerName);
    print sprintf(
        '<h2>Listing Bidder-level Filter Sets for ownerName "%s"</h2>',
         $ownerName
    );
    if (empty($result['filterSets'])) {
      print '<p>No Bidder-level Filter Sets found.</p>';
    } else {
      foreach ($result['filterSets'] as $filterSets) {
        $this->printResult($filterSets);
      }
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
    return 'RTB Troubleshooting: List Bidder-level Filter Sets';
  }
}

