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
 * This example illustrates how to retrieve a performance report.
 */
class ListPerformanceReport extends BaseExample {

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
            'name' => 'startDateTime',
            'display' => 'Start date (YYYY-MM-DD)',
            'required' => true
        ],
        [
            'name' => 'endDateTime',
            'display' => 'End date (YYYY-MM-DD)',
            'required' => true
        ],
        [
            'name' => 'maxResults',
            'display' => 'Maximum # of results',
            'required' => false
        ]
    ];
  }

  /**
   * @see BaseExample::run()
   */
  public function run() {
    $values = $this->formValues;

    $optParams = [];

    if (array_key_exists('maxResults', $values)) {
      $optParams['maxResults'] = $values['maxResults'];
    }

    try {
      $performanceReport = $this->service->performanceReport
          ->listPerformanceReport(
              $values['accountId'], $values['endDateTime'],
              $values['startDateTime'], $optParams);

      print '<h2>Found Performance Report</h2>';
      $this->printResult($performanceReport);
    } catch (Google_Service_Exception $ex) {
      if ($ex->getCode() == 404 || $ex->getCode() == 403) {
        print "<h1>Performance Report not found or can't access "
            . "performance report</h1>";
      } else {
        throw $ex;
      }
    }
  }

  /**
   * @see BaseExample::getName()
   */
  public function getName() {
    return 'ListPerformanceReport';
  }
}

