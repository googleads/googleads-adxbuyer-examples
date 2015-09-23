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
 * This example illustrates how to retrieve a performance report.
 *
 * Tags: performancereport.list
 *
 * @author Mark Saniscalchi <api.msaniscalchi@google.com>
 */
class ListPerformanceReport extends BaseExample {
  /**
   * (non-PHPdoc)
   * @see BaseExample::getInputParameters()
   */
  protected function getInputParameters() {
    return array(array('name' => 'account_id',
                       'display' => 'Account id',
                       'required' => true),
                 array('name' => 'start_date_time',
                       'display' => 'Start Date (YYYY-MM-DD)',
                       'required' => true),
                 array('name' => 'end_date_time',
                       'display' => 'End Date (YYYY-MM-DD)',
                       'required' => true),
                 array('name' => 'max_results',
                       'display' => 'Maximum # of results',
                       'required' => false));
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $optParams = array();

    if (array_key_exists('max_results', $values)) {
      $optParams['maxResults'] = $values['max_results'];
    }

    try {
      $performanceReport = $this->service->performanceReport->
          listPerformanceReport($values['account_id'],
              $values['end_date_time'], $values['start_date_time'],
              $optParams);

      print '<h2>Found Performance Report</h2>';
      $this->printResult($performanceReport);
    } catch (apiException $ex) {
      if ($ex->getCode() == 404 || $ex->getCode() == 403) {
        print '<h1>Performance Report not found or can\'t access performance' .
            'report</h1>';
      } else {
        throw $ex;
      }
    }
  }

  /**
   * (non-PHPdoc)
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'ListPerformanceReport';
  }
}

