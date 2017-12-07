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
 * This example illustrates how to insert a new PretargetingConfig.
 */
class InsertPretargetingConfig extends BaseExample {

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
            'name' => 'configName',
            'display' => 'Config name',
            'required' => true
        ],
        [
            'name' => 'isActive',
            'display' => 'Is active (True/False)',
            'required' => true
        ],
        [
            'name' => 'creativeType',
            'display' => 'Creative type',
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

  public function run() {
    $values = $this->formValues;

    $config = new Google_Service_AdExchangeBuyer_PretargetingConfig();
    $config->accountId = $values['accountId'];
    $config->configName = $values['configName'];
    $config->isActive = $values['isActive'];
    $config->creativeType = [$values['creativeType']];
    $config->dimensions = [
        'width' => $values['width'],
        'height' => $values['height']
    ];

    $config = $this->service->pretargetingConfig->insert($values['accountId'],
        $config);
    print '<h2>Inserted PretargetingConfig</h2>';
    $this->printResult($config);
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Insert PretargetingConfig';
  }
}

