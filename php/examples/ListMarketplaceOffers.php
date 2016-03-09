<?php
/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Require the base class.
require_once __DIR__ . "/../BaseExample.php";

/**
 * This example illustrates how to list marketplace offers.
 *
 */
class ListMarketplaceOffers extends BaseExample {

  /**
   * (non-PHPdoc)
   * @see BaseExample::run()
   */
  public function run() {
    $result = $this->service->marketplaceoffers->search();
    print '<h2>Listing marketplace offers</h2>';
    if (isset($result['offers']) && count($result['offers']) > 0) {
      foreach ($result['offers'] as $offer) {
        $this->printResult($offer);
      }
    } else {
      print '<p>No offer found</p>';
    }
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Marketplace: List Offers';
  }
}

