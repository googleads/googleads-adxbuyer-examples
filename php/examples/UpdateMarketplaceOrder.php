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
 * This example illustrates how to create an order.
 *
 */
class UpdateMarketplaceOrder extends BaseExample {
  /**
   * (non-PHPdoc)
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return array(array('name' => 'order_id',
                       'display' => 'Order id',
                       'required' => true),
                 array('name' => 'update_action',
                       'display' => 'Update Action',
                       'required' => true));
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $orderId = $values['order_id'];
    $updateAction = $values['update_action'];
    $order = $this->service->marketplaceorders->get($orderId);

    print '<h2>Updated marketplace order</h2>';
    $order = $this->service->marketplaceorders->update($orderId,
        $order->revisionNumber, $updateAction, $order);
    $this->printResult($order);
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Marketplace: Update Order';
  }
}

