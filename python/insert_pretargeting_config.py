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

"""This example illustrates how to insert a new pretargeting config.

Tags: PretargetingConfig.insert
"""

__author__ = 'lukiesd@google.com (Dean Lukies)'

import pprint
import sys

from oauth2client.client import AccessTokenRefreshError
import util


def main(ad_exchange_buyer, account_id, body):
  # Construct the request.
  request = ad_exchange_buyer.pretargetingConfig().insert(
      accountId=account_id, body=body)

  # Execute request and print response.
  pprint.pprint(request.execute())

if __name__ == '__main__':
  try:
    service = util.GetService()

    # Set the account id to add the creative to
    ACCOUNT_ID = int('INSERT_ACCOUNT_ID')
    # Create a body containing the PretargetingConfig's details.
    BODY = {
        'configName': 'INSERT_CONFIG_NAME',
        'isActive': bool('INSERT_TRUE_OR_FALSE'),
        'creativeType': ['INSERT_CREATIVE_TYPE'],
        'dimensions': [{
            'width': int('INSERT_WIDTH'),
            'height': int('INSERT_HEIGHT')
        }]
    }

    if BODY['configName'] == 'INSERT_CONFIG_NAME':
      raise Exception('The configName was not set.')
    if BODY['isActive'] == 'INSERT_TRUE_OR_FALSE':
      raise Exception('The isActive was not set.')
    if BODY['creativeType'][0] == 'INSERT_CREATIVE_TYPE':
      raise Exception('The creativeType was not set.')
    if BODY['width'] == 'INSERT_WIDTH':
      raise Exception('The width was not set.')
    if BODY['height'] == 'INSERT_HEIGHT':
      raise Exception('The height was not set.')
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
    print 'Did you set account_id, width and height to an integer?'
    sys.exit()

  main(service, ACCOUNT_ID, BODY)
