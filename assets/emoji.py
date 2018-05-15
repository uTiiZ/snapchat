import re

if __name__ == '__main__':
	f = open('emoji.html', 'rb')
	for line in f.readlines() :
		match =  re.match(r".*https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/(?P<emoji>.*).png", str(line))
		if match :
			print(match.group('emoji'))