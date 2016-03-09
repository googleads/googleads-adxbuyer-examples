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
 * This example illustrates how to update a deal.
 *
 */
class UpdateMarketplaceDeal extends BaseExample {
  /**
   * (non-PHPdoc)
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return array(array('name' => 'order_id',
                       'display' => 'Order Id',
                       'required' => true));
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $orderId = $values['order_id'];

    $order = $this->service->marketplaceorders->get($orderId);
    $deals = $this->service->marketplacedeals->listMarketplacedeals($orderId);

    $updateOrders =
        new Google_Service_AdExchangeBuyer_EditAllOrderDealsRequest();
    $updateOrders->order = $order;
    $updateOrders->deals = $deals;
    $updateOrders->updateAction = 'accept';
    $updateOrders->orderRevisionNumber = $order->revisionNumber;

    $order = $this->service->marketplacedeals->update($orderId, $updateOrders);
    print '<h2>Updated marketplace deal - marked as accepted</h2>';
    $this->printResult($updateOrders);
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Marketplace: Update Deal';
  }
}

