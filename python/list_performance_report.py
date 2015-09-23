#!/usr/bin/python
#
# Copyright 2015 Google Inc. All Rights Reserved.
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

"""This example lists the given Account's Performance Report.

Tags: PerformanceReport.list
"""

__author__ = 'api.msaniscalchi@gmail.com (Mark Saniscalchi)'

import argparse
from datetime import datetime
from datetime import timedelta
import pprint
import sys

from oauth2client.client import AccessTokenRefreshError
import util

# Define default start and end times.
start = datetime.now()
end = start + timedelta(weeks=1)
fmt = '%Y-%m-%d'

# Optional arguments; overrides default values if set.
parser = argparse.ArgumentParser(description='Retrieves list of performance '
                                 'metrics.')
parser.add_argument('-a', '--account_id', required=False, type=int,
                    help=('The integer id of the account you\'re retrieving '
                          'the report for.'))
parser.add_argument('-s', '--start_date_time', required=False,
                    default=start.strftime(fmt),
                    help=('The start time of the report in ISO 8601 '
                          'timestamp format using UTC. (YYYY-MM-DD)'))
parser.add_argument('-e', '--end_date_time', required=False,
                    default=end.strftime(fmt),
                    help=('The end time of the report in ISO 8601 timestamp '
                          'format using UTC. (YYYY-MM-DD)'))
parser.add_argument('-m', '--max_results', required=False, type=int,
                    default=util.MAX_PAGE_SIZE,
                    help=('The maximum number of entries returned on one '
                          'result page.'))


def main(ad_exchange_buyer, account_id, start_date_time, end_date_time,
         max_results):
  # Construct the request.
  request = ad_exchange_buyer.performanceReport().list(
      accountId=account_id,
      startDateTime=start_date_time,
      endDateTime=end_date_time,
      maxResults=max_results)

  # Execute request and print response.
  pprint.pprint(request.execute())

if __name__ == '__main__':
  try:
    service = util.GetService()
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
      MAX_RESULTS = util.MAX_PAGE_SIZE

    if START_DATE_TIME == 'YYYY-MM-DD':
      raise Exception('The report\'s startDateTime was not set.')

    if END_DATE_TIME == 'YYYY-MM-DD':
      raise Exception('The report\'s endDateTime was not set.')

  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in util.py?'
    sys.exit()
  except AccessTokenRefreshError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you set the correct Service Account Email in util.py?'
    sys.exit()
  except ValueError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you set account_id to an integer?'
    sys.exit()

  main(service, ACCOUNT_ID, START_DATE_TIME, END_DATE_TIME, MAX_RESULTS)
