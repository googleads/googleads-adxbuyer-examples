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

"""This example lists the user's Accounts."""

import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


def main(ad_exchange_buyer):
  try:
    # Construct and execute the request.
    accounts = ad_exchange_buyer.accounts().list().execute()
    if 'items' in accounts:
      print('Found the following accounts for current user:')
      for account in accounts['items']:
        pprint.pprint(account)
    else:
      print('No accounts found.')
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  try:
    service = samples_util.GetService()
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service)
