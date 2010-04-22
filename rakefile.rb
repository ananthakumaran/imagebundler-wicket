#
# Copyright (C) 2010 Anantha Kumaran <ananthakumaran@gmail.com>
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

task :deploy do
  temp = "/media/Studies/temp"
  puts `git clone -l -s -b gh-pages . #{temp}`
  if system("mvn deploy")
      puts `cd #{temp} && git add -A && git commit -m "deploying snapshots" && git push origin gh-pages`
      puts `git push origin gh-pages`
      puts " snapshot deployed deployed successfully "
  else
    puts " snapshot could not deployed "
  end
end

task :release do
  temp = "/media/Studies/temp"
  puts `git clone -l -s -b gh-pages . #{temp}`
  if system("mvn release:perform")
      puts `cd #{temp} && git add -A && git commit -m "releasing artifacts" && git push origin gh-pages`
      puts `git push origin gh-pages`
      puts " artifact released successfully "
  else
    puts " could not release artifact "
  end
end
