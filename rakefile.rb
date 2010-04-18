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
