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

"""This example illustrates how to insert a new pretargeting config."""


import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID'
DEFAULT_CONFIG_NAME = 'INSERT_CONFIG_NAME'
DEFAULT_IS_ACTIVE = 'INSERT_IS_ACTIVE'
DEFAULT_CREATIVE_TYPE = 'INSERT_CREATIVE_TYPE'
DEFAULT_WIDTH = 'INSERT_WIDTH'
DEFAULT_HEIGHT = 'INSERT_HEIGHT'


def main(ad_exchange_buyer, account_id, body):
  try:
    # Construct and execute the request.
    pretargeting_config = ad_exchange_buyer.pretargetingConfig().insert(
        accountId=account_id, body=body).execute()
    print 'Successfully inserted pretargeting config.'
    pprint.pprint(pretargeting_config)
  except HttpError as e:
    print e


if __name__ == '__main__':
  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Inserts a new pretargeting '
                                   'configuration.')
  parser.add_argument('-a', '--account_id', required=False, type=int,
                      default=DEFAULT_ACCOUNT_ID,
                      help=('The integer id of the account you\'re adding the '
                            'pretargeting configuration to.'))
  parser.add_argument('-c', '--config_name', required=False,
                      default=DEFAULT_CONFIG_NAME,
                      help=('Name of the PretargetingConfig. Should be '
                            'unique.'))
  parser.add_argument('-i', '--is_active', required=False, type=bool,
                      default=DEFAULT_IS_ACTIVE,
                      help=('Whether the PretargetingConfig is active.'))
  parser.add_argument('-t', '--creative_type', required=False,
                      default=DEFAULT_CREATIVE_TYPE,
                      help=('The type of Creative being targeted.'))
  parser.add_argument('-w', '--width', required=False, type=int,
                      default=DEFAULT_WIDTH,
                      help=('The width of creatives being targeted.'))
  parser.add_argument('-ht', '--height', required=False, type=int,
                      default=DEFAULT_HEIGHT,
                      help=('The height of creatives being targeted.'))
  args = parser.parse_args()

  # Create a body containing the required fields.
  BODY = {
      'configName': args.config_name,
      'isActive': args.is_active,
      'creativeType': [args.creative_type],
      'dimensions': [{
          'width': args.width,
          'height': args.height
      }]
  }

  try:
    service = samples_util.GetService()
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit(1)

  main(service, args.account_id, BODY)

