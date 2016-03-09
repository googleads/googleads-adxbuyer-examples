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
 * This example illustrates how to create a marketplace order.
 *
 */
class InsertMarketplaceOrder extends BaseExample {
  /**
   * (non-PHPdoc)
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return array(array('name' => 'order_name',
                       'display' => 'Order Name',
                       'required' => true),
                 array('name' => 'seller_account_id',
                       'display' => 'Seller account id',
                       'required' => true),
                 array('name' => 'seller_sub_account_id',
                       'display' => 'Seller sub account id',
                       'required' => true),
                 array('name' => 'buyer_account_id',
                       'display' => 'Buyer account id',
                       'required' => true));
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $order = new Google_Service_AdExchangeBuyer_MarketplaceOrder();
    $order->name = $values['order_name'];
    $order->seller = new Google_Service_AdExchangeBuyer_Seller();
    $order->seller->accountId = $values['seller_account_id'];
    $order->seller->subAccountId = $values['seller_sub_account_id'];
    $order->buyer = new Google_Service_AdExchangeBuyer_Buyer();
    $order->buyer->accountId = $values['buyer_account_id'];

    $orderRequest = new Google_Service_AdExchangeBuyer_CreateOrdersRequest();
    $orderRequest->setOrders(array($order));

    $orderRequest = $this->service->marketplaceorders->insert($orderRequest);
    print '<h2>Inserted marketplace order</h2>';
    $this->printResult($orderRequest);
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Marketplace: Insert Order';
  }
}

