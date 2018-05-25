import re
import requests

if __name__ == '__main__':
	f = open('emoji.html', 'rb')
	txt = open('emoji.txt', 'r+')
	txt.truncate()
	for line in f.readlines() :
		match =  re.match(r".*(?P<url>https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/(?P<emoji>.*).png)\"", str(line))
		if match :
			img_data = requests.get(match.group('url')).content
			with open('img/' + match.group('emoji') + '.png', 'wb') as handler:
			    handler.write(img_data)
			txt.write("\"" + match.group('emoji') + "\", ")
			print(match.group('emoji'))

