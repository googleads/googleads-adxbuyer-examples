#!/usr/bin/python
#
# Copyright 2016 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example lists the given account's Performance Report."""

import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_END_DATE_TIME = 'INSERT_END_DATE_TIME_HERE'      # YYYY-MM-DD
DEFAULT_START_DATE_TIME = 'INSERT_START_DATE_TIME_HERE'  # YYYY-MM-DD
DEFAULT_MAX_PAGE_SIZE = samples_util.MAX_PAGE_SIZE


def main(ad_exchange_buyer, account_id, start_date_time, end_date_time,
         max_results):
  try:
    # Construct and execute the request.
    report = ad_exchange_buyer.performanceReport().list(
        accountId=account_id,
        startDateTime=start_date_time,
        endDateTime=end_date_time,
        maxResults=max_results).execute()
    print('Successfully retrieved the report.')
    pprint.pprint(report)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Retrieves list of performance '
                                   'metrics.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re retrieving '
                            'the report for.'))
  parser.add_argument('-s', '--start_date_time', required=False,
                      default=DEFAULT_START_DATE_TIME,
                      help=('The start time of the report in ISO 8601 '
                            'timestamp format using UTC. (YYYY-MM-DD)'))
  parser.add_argument('-e', '--end_date_time', required=False,
                      default=DEFAULT_END_DATE_TIME,
                      help=('The end time of the report in ISO 8601 timestamp '
                            'format using UTC. (YYYY-MM-DD)'))
  parser.add_argument('-m', '--max_results', required=False, type=int,
                      default=DEFAULT_MAX_PAGE_SIZE,
                      help=('The maximum number of entries returned on one '
                            'result page.'))
  args = parser.parse_args()

  if args.account_id:
    ACCOUNT_ID = args.account_id
    START_DATE_TIME = args.start_date_time
    END_DATE_TIME = args.end_date_time
    MAX_RESULTS = args.max_results
  else:
    ACCOUNT_ID = int('INSERT_ACCOUNT_ID')
    START_DATE_TIME = 'YYYY-MM-DD'  # Insert startDateTime here.
    END_DATE_TIME = 'YYYY-MM-DD'  # Insert endDateTime here.
    MAX_RESULTS = samples_util.MAX_PAGE_SIZE

  try:
    service = samples_util.GetService()
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, ACCOUNT_ID, START_DATE_TIME, END_DATE_TIME, MAX_RESULTS)

