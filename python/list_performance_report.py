#!/usr/bin/python
#
# Copyright 2014 Google Inc. All Rights Reserved.
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

import pprint
import sys

from oauth2client.client import AccessTokenRefreshError
import util


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
