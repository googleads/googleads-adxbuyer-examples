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
 * This example illustrates how to insert a new PretargetingConfig.
 *
 * Tags: pretargetingconfig.insert
 *
 * @author Mark Saniscalchi <api.msaniscalchi@google.com>
 */
class InsertPretargetingConfig extends BaseExample {
  protected function getInputParameters() {
    return array(array('name' => 'account_id',
                       'display' => 'Account id',
                       'required' => true),
                 array('name' => 'config_name',
                       'display' => 'ConfigName',
                       'required' => true),
                 array('name' => 'is_active',
                       'display' => 'Is Active (True/False)',
                       'required' => true),
                 array('name' => 'creative_type',
                       'display' => 'Creative Type',
                       'required' => true),
                 array('name' => 'width',
                       'display' => 'Width',
                       'required' => true),
                 array('name' => 'height',
                       'display' => 'Height',
                       'required' => true));
  }

  public function run() {
    $values = $this->formValues;

    $config = new Google_Service_AdExchangeBuyer_PretargetingConfig();
    $config->account_id = $values['account_id'];
    $config->configName = $values['config_name'];
    $config->isActive = $values['is_active'];
    $config->creativeType = array($values['creative_type']);
    $config->dimensions = array('width' => $values['width'],
                                'height' => $values['height']);

    $config = $this->service->pretargetingConfig->insert($values['account_id'],
        $config);
    print '<h2>Inserted PretargetingConfig</h2>';
    $this->printResult($config);
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'Insert PretargetingConfig';
  }
}

