# URLPercentDecoder
A simple android app in Kotlin that decodes [percent encoded url](https://en.wikipedia.org/wiki/Percent-encoding "percent encoded url") to normal url

**Why this app?**

You're in a website that has links to another site (Example: slickdeals).  Sometimes this link has embedded trackers in them that gets all your information before redirecting you to another link. You would never notice it unless you have an ad blocker (like pihole) enabled that blocks this tracker that fails to load the website or when you hover over the link and you see a strange url instead of the actual url

An example in slickdeals with the link to the deal looks like this:
https://click.linksynergy.com/deeplink?u1=cd58d8aefea411e9a788aa402ec8805f0INT&id=lw9MynSeamY&mid=44583&murl=https%3A%2F%2Fwww.newegg.com%2Fsandisk-model-sdddc2-064g-g46-64gb%2Fp%2FN82E16820173270%3Fsdtid%3D13373209

You can see two websites in this link
1.  https://click.linksynergy.com/deeplink?u1=cd58d8aefea411e9a788aa402ec8805f0INT&id=lw9MynSeamY&mid=44583&murl=
2. https%3A%2F%2Fwww.newegg.com%2Fsandisk-model-sdddc2-064g-g46-64gb%2Fp%2FN82E16820173270%3Fsdtid%3D13373209

You can access the first link (which is a tracker) but the second link is encoded which has to be decoded. 

This app will take the whole url, splits all url in the links, decodes it and gives you a direct way to open the actual link in browser.

**A small demo:**

![Example GIF](example.gif)
