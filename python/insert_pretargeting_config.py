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

"""This example illustrates how to insert a new pretargeting config.

Tags: PretargetingConfig.insert
"""

__author__ = 'lukiesd@google.com (Dean Lukies)'

import argparse
import pprint
import sys

from oauth2client.client import AccessTokenRefreshError
import util

# Optional arguments; overrides default values if set.
parser = argparse.ArgumentParser(description='Inserts a new pretargeting '
                                 'configuration.')
parser.add_argument('-a', '--account_id', required=False, type=int,
                    help=('The integer id of the account you\'re adding the '
                          'pretargeting configuration to.'))
parser.add_argument('-c', '--config_name', required=False,
                    help=('Name of the PretargetingConfig. Should be unique.'))
parser.add_argument('-i', '--is_active', required=False, type=bool,
                    default=False,
                    help=('Whether the PretargetingConfig is active.'))
parser.add_argument('-t', '--creative_type', required=False,
                    default='PRETARGETING_CREATIVE_TYPE_HTML',
                    help=('The type of Creative being targeted.'))
parser.add_argument('-w', '--width', required=False, type=int, default=300,
                    help=('The width of creatives being targeted.'))
parser.add_argument('-ht', '--height', required=False, type=int, default=250,
                    help=('The height of creatives being targeted.'))


def main(ad_exchange_buyer, account_id, body):
  # Construct the request.
  request = ad_exchange_buyer.pretargetingConfig().insert(
      accountId=account_id, body=body)

  # Execute request and print response.
  pprint.pprint(request.execute())

if __name__ == '__main__':
  try:
    service = util.GetService()
    args = parser.parse_args()

    # Set the account id and create request body.
    if args.account_id and args.config_name:
      ACCOUNT_ID = args.account_id
      BODY = {
          'configName': args.config_name,
          'isActive': args.is_active,
          'creativeType': [args.creative_type],
          'dimensions': [{
              'width': args.width,
              'height': args.height
          }]
      }
    else:
      ACCOUNT_ID = int('INSERT_ACCOUNT_ID')
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
    if BODY['dimensions'][0]['width'] == 'INSERT_WIDTH':
      raise Exception('The width was not set.')
    if BODY['dimensions'][0]['height'] == 'INSERT_HEIGHT':
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
