import re

if __name__ == '__main__':
	f = open('emoji.html', 'rb')
	txt = open('emoji.txt', 'r+')
	txt.truncate()
	for line in f.readlines() :
		match =  re.match(r".*https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/(?P<emoji>.*).png\"", str(line))
		if match :
			txt.write("\"" + match.group('emoji') + "\", ")
			print(match.group('emoji'))

