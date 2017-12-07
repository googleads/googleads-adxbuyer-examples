<?php
/**
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

require_once __DIR__ . '/../../BaseExample.php';

/**
 * This example illustrates how to submit a new creative to the
 * verification pipeline.
 */
class SubmitCreative extends BaseExample {

  /**
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return [
        [
            'name' => 'accountId',
            'display' => 'Account ID',
            'required' => true
        ],
        [
            'name' => 'buyerCreativeId',
            'display' => 'Buyer creative ID',
            'required' => true
        ],
        [
            'name' => 'advertiserName',
            'display' => 'Advertiser name',
            'required' => true
        ],
        [
            'name' => 'htmlSnippet',
            'display' => 'HTML snippet',
            'required' => true
        ],
        [
            'name' => 'clickThroughUrls',
            'display' => 'Click through URLs',
            'required' => true
        ],
        [
            'name' => 'width',
            'display' => 'Width',
            'required' => true
        ],
        [
            'name' => 'height',
            'display' => 'Height',
            'required' => true
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $creative = new Google_Service_AdExchangeBuyer_Creative();
    $creative->accountId = $values['accountId'];
    $creative->buyerCreativeId = $values['buyerCreativeId'];
    $creative->advertiserName = $values['advertiserName'];
    $creative->HTMLSnippet = $values['htmlSnippet'];
    $creative->clickThroughUrl = explode(',', $values['clickThroughUrls']);
    $creative->width = $values['width'];
    $creative->height = $values['height'];

    $creative = $this->service->creatives->insert($creative);
    print '<h2>Submitted creative</h2>';
    $this->printResult($creative);
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Submit Creative';
  }
}

