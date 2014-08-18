var Question = (function(){

  ///////////////////
  // Vars
  ///////////////////

  var questionId;
  var reviewId;
  var gettingReviews=false;
  var moreReviews=false;
  var startIndexReview=0;
  var PAGE_SIZE=10; // If changes, update server count as well.

  ///////////////////
  // Controller
  ///////////////////

  var create=function() {
    gettingReviews=false;
    moreReviews=false;
    startIndexReview=0;
    //setOnlineListeners();
    createQuestionLayout();
    setUpPage();
    setUpReviews();
    window.onscroll=function(){ checkForMoreReviews(); };
  }

  var setUpPage=function() {

    // Check if logged in
    var questionRevUser=getCookie("questionRevUser");
    QuestionRevUser.isLoggedIn=false;
    if (questionRevUser!="") {
      QuestionRevUser.isLoggedIn=true;
    }

    // If logged in and online, can edit
    QuestionRevUser.canEdit=QuestionRevUser.isLoggedIn && navigator.onLine;

    // Show 'Edit link' if can edit
    var questionEditLink=document.getElementById("questionEditLink");
    if (QuestionRevUser.canEdit) {
       questionEditLink.style.display='inline';
    } else {
       questionEditLink.style.display='none';
    }
  }

  var setUpReviews=function() {
    if (reviewId) {
      getReviewsDataById();
      var allReviewsLink=document.getElementById("allReviewsLink");
      allReviewsLink.style.display="inline";
      allReviewsLink.addEventListener('click', function(e){e.preventDefault();Question.display(questionId);}, false);
    } else {
      getReviewsData();
    }
  }

  /*
  var setOnlineListeners=function() {
    document.body.addEventListener("offline", setUpPage, false)
    document.body.addEventListener("online", setUpPage, false);
  }
  */

  var checkForMoreReviews=function() {
    var moreIndicator=document.getElementById("moreIndicator");
    if (moreReviews && !gettingReviews && moreIndicator && elementInViewport(moreIndicator)) {
      gettingReviews=true;
      startIndexReview+=PAGE_SIZE;
      getReviewsData();
    }
  }

  var getCachedData=function() {
      var xmlDoc=null;
      var cachedResponse=localStorage.getItem(getDataKey());
      if (cachedResponse) {
        var parser=new DOMParser();
        xmlDoc=parser.parseFromString(cachedResponse,"text/xml");
      }
      return xmlDoc;
  }

  var getReviewsData=function() {
    var qsString=getQueryStrings();
    var reload=false;
    if (qsString && qsString.reload && qsString.reload=="true") {
      reload=true;
    }

    // If online, get from server.  Else get from cache.
    if (navigator.onLine) {
      if (!reload) {
        var xmlDoc=getCachedData();
        if (xmlDoc) {
          displayData(xmlDoc);
        }
      }
      sendRequest('/reviewsXml?questionId='+questionId+'&start=' + startIndexReview, handleReviewsDataRequest, displayCachedData);
    } else {
      displayCachedData();
    }
  }

  var getDataKey=function() {
    if (reviewId) {
      return "REVIEW_"+reviewId;
    } else {
      return "DISH_"+questionId+"_"+startIndexReview;
    }
  }

  var getReviewsDataById=function() {
    var qsString=getQueryStrings();
    var reload=false;
    if (qsString && qsString.reload && qsString.reload=="true") {
      reload=true;
    }

    // If online, get from server.  Else get from cache.  // TODO - Update cached data?
    if (navigator.onLine) {
      if (!reload) {
        var xmlDoc=getCachedData();
        if (xmlDoc) {
          displayData(xmlDoc);
        }
      }
      sendRequest('/reviewsXml?reviewId='+reviewId, handleReviewsDataRequest, displayCachedData);
    } else {
      displayCachedData();
    }
  }

  var handleReviewsDataRequest=function(req) {
    var display=true;
    var qsString=getQueryStrings();
    var reload=qsString && qsString.reload && qsString.reload=="true";
    if (!reload) {
      var cachedResponse=localStorage.getItem(getDataKey());
      if (cachedResponse!=null) {
        var display=false;
        if (cachedResponse!=req.responseText) {
          var response=confirm("Do you want to display new data?");
          if (response) {
            window.location.href=window.location.href+"&reload=true";
          }
        }
      }
    }

    // Save in local storage in case app goes offline
    setItemIntoLocalStorage(getDataKey(), req.responseText);

    // Process response
    if (display) {
      var xmlDoc=req.responseXML;
      displayData(xmlDoc);
    }
  }

  var postReviewToFacebook=function(aReviewId) {
    var reviewLink="http://questionrev.appspot.com/question?questionId=" + questionId + "&reviewId=" + aReviewId;
    var reviewImageLink="http://questionrev.appspot.com/reviewThumbNailImage?reviewId" + aReviewId;
    var questionName=document.getElementById("questionName").innerHTML;
    var storeName=document.getElementById("storeName").innerHTML;
    var publish = {
      method: 'feed',
      name: questionName + " at " + storeName,
      link: reviewLink,
      picture: reviewImageLink
    };
    FB.init();
    FB.ui(publish);
  }

  ///////////////////
  // View
  ///////////////////

  var createQuestionLayout=function () {
    createQuestionNav();
    createQuestionSections();
  }

  var createQuestionNav=function() {
    var nav=document.getElementById("nav");
    removeChildrenFromElement(nav);

    // List
    var navUl=document.createElement("ul");
    nav.appendChild(navUl);
    navUl.setAttribute("id","navlist");

    // Main
    var navItem=document.createElement("li");
    navUl.appendChild(navItem);
    navItem.setAttribute("id","main");
    var navItemLink=document.createElement("a");
    navItem.appendChild(navItemLink);
    navItemLink.setAttribute("href","#");
    navItemLink.addEventListener('click', function(e){e.preventDefault();Stores.linkTo();}, false);
    navItemLink.appendChild(document.createTextNode("Main"));

    // Store
    var navItem=document.createElement("li");
    navUl.appendChild(navItem);
    var navItemLink=document.createElement("a");
    navItem.appendChild(navItemLink);
    navItemLink.setAttribute("href","#");
    navItemLink.setAttribute("id","storeLink");
    var storeName=document.createElement("span");
    navItemLink.appendChild(storeName);
    storeName.setAttribute("id","storeName");

    // Offline
    var navItem=document.createElement("li");
    navUl.appendChild(navItem);
    navItem.setAttribute("id","offline");
    navItem.setAttribute("style","display:none");
    navItem.setAttribute("class","nw");
    navItem.appendChild(document.createTextNode("Offline"));
  }

  var createQuestionSections=function() {
    // Clear content
    var content=document.getElementById("content");
    removeChildrenFromElement(content);

    var sectionData=document.createElement("section");
    content.appendChild(sectionData);

    // Question name
    var questionName=document.createElement("span");
    sectionData.appendChild(questionName);
    questionName.setAttribute("id","questionName");

    // Space between name and edit link
    sectionData.appendChild(document.createTextNode(" "));

    // Edit link
    var editLink=document.createElement("a");
    sectionData.appendChild(editLink);
    editLink.setAttribute("href","/questionUpdate?questionId=" + questionId);
    editLink.setAttribute("class","edit");
    editLink.setAttribute("style","display:none");
    editLink.setAttribute("id","questionEditLink");
    editLink.appendChild(document.createTextNode("Edit"));

    // Space
    sectionData.appendChild(document.createTextNode(" "));

    // All reviews link
    var allReviewsLink=document.createElement("a");
    sectionData.appendChild(allReviewsLink);
    allReviewsLink.setAttribute("href","#");
    allReviewsLink.addEventListener('click', function(e){e.preventDefault();Question.linkTo(questionId);}, false);
    allReviewsLink.setAttribute("class","more");
    allReviewsLink.setAttribute("style","display:none");
    allReviewsLink.setAttribute("id","allReviewsLink");
    allReviewsLink.appendChild(document.createTextNode("All Reviews"));

    // Waiting for data
    var waitingForData=document.createElement("progress");
    sectionData.appendChild(waitingForData);
    //waitingForData.setAttribute("style","display:none");
    waitingForData.setAttribute("id","waitingForData");
    waitingForData.setAttribute("title","Waiting for data");

    // Question data
    var questionData=document.createElement("div");
    sectionData.appendChild(questionData);
    questionData.setAttribute("class","data");
    questionData.setAttribute("id","questionData");

    // More indicator
    var moreIndicator=document.createElement("progress");
    sectionData.appendChild(moreIndicator);
    moreIndicator.setAttribute("style","display:none");
    moreIndicator.setAttribute("id","moreIndicator");
    moreIndicator.setAttribute("title","Loading more");
  }

  var displayCachedData=function() {
    var xmlDoc=getCachedData();
    if (xmlDoc) {
      displayData(xmlDoc);
    } else {
      displayTableNoCachedData();
    }
  }

  var displayData=function(xmlDoc) {
    document.getElementById("waitingForData").style.display="none";
    var table=document.getElementById("reviews");
    var newTable=false;
    if (table==null) {
      newTable=true;
      table=createTable();
      document.getElementById("questionData").appendChild(table);
    }

    var question=xmlDoc.getElementsByTagName("question")[0];

    // Store name
    var storeName=question.getAttribute("storeName");
    var storeNameTag=document.getElementById("storeName");
    removeChildrenFromElement(storeNameTag);
    storeNameTag.appendChild(document.createTextNode(storeName));

    // Store link
    var storeId=question.getAttribute("storeId");
    var storeLinkTag=document.getElementById("storeLink");
    storeLinkTag.addEventListener('click', function(e){e.preventDefault();Store.linkTo(storeId);}, false);

    // Question Name
    var questionName=question.getAttribute("questionName");
    var questionNameTag=document.getElementById("questionName");
    removeChildrenFromElement(questionNameTag);
    questionNameTag.appendChild(document.createTextNode(questionName));

    // Title
    var title=document.getElementById("title");
    removeChildrenFromElement(title);
    title.appendChild(document.createTextNode(questionName));

    // Process reviews
    var reviews=xmlDoc.getElementsByTagName("review");
    var moreIndicator=document.getElementById("moreIndicator");
    if (reviews.length==0){
      moreReviews=false;
      moreIndicator.style.display="none";
      if (newTable) {
        table.appendChild(createTableRowForNoData());
      }
    } else {
      // Check if more
      if (reviews.length<PAGE_SIZE){
        moreReviews=false;
        moreIndicator.style.display="none";
      } else {
        moreReviews=true;
      }

      // Make row for each review
      for (var i=0;i<reviews.length;i++) {
        var review=reviews[i];
        table.appendChild(createTableRowForReview(review));
      }

      // Show 'more' after table is populated
      if (moreReviews) {
        moreIndicator.style.display="inline";
      }

      // Parse for Facebook tags
      if (typeof(FB) != "undefined") {
        FB.XFBML.parse(table);
      }

      gettingReviews=false;
      checkForMoreReviews();
    }
  }

  ///////////////////
  // Display
  ///////////////////

  var createTable=function() {
    var table=document.createElement("table");
    table.setAttribute("id","reviews");
    var tr=document.createElement("tr");
    table.appendChild(tr);

    // Review
    var thReview=document.createElement("th");
    tr.appendChild(thReview);
    thReview.appendChild(document.createTextNode("Review"));

    // Show Add link
    if (QuestionRevUser.canEdit) {
      var addLink=document.createElement("a");
      addLink.setAttribute("href","/reviewAdd?questionId="+questionId);
      addLink.setAttribute("class","add addTh");
      addLink.appendChild(document.createTextNode("Add"));
      thReview.appendChild(addLink);
    }

    // Time
    var thTime=document.createElement("th");
    tr.appendChild(thTime);
    thTime.appendChild(document.createTextNode("Time Ago"));

    // Agree
    var thVote=document.createElement("th");
    tr.appendChild(thVote);
    thVote.appendChild(document.createTextNode("Agree"));

    // Image
    var thImage=document.createElement("th");
    tr.appendChild(thImage);
    thImage.appendChild(document.createTextNode("Image"));

    return table;
  }

  var createTableRowForReview=function(review) {
    var tr=document.createElement("tr");
    var currentSeconds=new Date().getTime()/1000;

    // Attributes
    var reviewId=review.getAttribute("reviewId");
    var reviewText=review.getAttribute("text");
    var vote=review.getAttribute("yes");
    var time=review.getAttribute("time");
    var usersOwn=review.getAttribute("user")=="true";
    var userId=review.getAttribute("userId");
    tr.setAttribute("id","reviewId"+reviewId);
    tr.setAttribute("reviewText",reviewText);

    // Description
    var descReview=document.createElement("td");
    tr.appendChild(descReview);
    descReview.appendChild(document.createTextNode(reviewText));
    descReview.appendChild(document.createTextNode(" "));

    // Edit and Facebook button
    if (usersOwn) {
      var editLink=document.createElement("a");
      editLink.setAttribute("href","/reviewUpdate?reviewId="+reviewId);
      editLink.setAttribute("class","edit");
      editLink.appendChild(document.createTextNode("Edit"));
      descReview.appendChild(editLink);
      descReview.appendChild(document.createTextNode(" "));

      var postButton=document.createElement("button");
      postButton.setAttribute("onclick","Question.postReviewToFacebook(\"" + reviewId + "\");return false;");
      postButton.appendChild(document.createTextNode("Share on Facebook"));
      descReview.appendChild(postButton);
    } else {
      // Add name from Facebook id.
      // Note, adding with createElementNS didn't work.  So using innerHTML.
      var fbName=document.createElement("span");
      descReview.appendChild(fbName);
      fbName.innerHTML=' - <fb:name uid="' + userId + '" useyou="false" linked="true"></fb:name>';
    }

    // Time Ago
    var timeReview=document.createElement("td");
    var elapsedTime=getElapsedTime(parseInt(review.getAttribute("time")),currentSeconds);
    timeReview.appendChild(document.createTextNode(elapsedTime));
    tr.appendChild(timeReview);

    // Vote
    if (QuestionRevUser.canEdit) {
        var voteDisplay=document.createElement("td");
        var voteLink=document.createElement("a");
        voteLink.setAttribute("href","/reviewVote?reviewId="+reviewId);
        voteLink.setAttribute("class","center");
        voteLink.appendChild(document.createTextNode(vote));
        voteDisplay.appendChild(voteLink);
        tr.appendChild(voteDisplay);
    } else {
        var voteDisplay=document.createElement("td");
        voteDisplay.setAttribute("class","center");
        voteDisplay.appendChild(document.createTextNode(vote));
        tr.appendChild(voteDisplay);
    }

    // Image
    var imageCell=document.createElement("td");
    if (review.getAttribute("img")=="true") {
      var imageLink=document.createElement("a");
      imageLink.setAttribute("href","/reviewImageUpdate?reviewId="+reviewId);
      var image=document.createElement("img");
      image.setAttribute("src","/reviewThumbNailImage?reviewId="+reviewId);
      imageLink.appendChild(image);
      imageCell.appendChild(imageLink);
    } else if (usersOwn) {
      var imageLink=document.createElement("a");
      imageLink.setAttribute("class","add");
      imageLink.setAttribute("href","/reviewImageUpdate?reviewId="+reviewId);
      imageLink.appendChild(document.createTextNode("Add"));
      imageCell.appendChild(imageLink)
    }
    tr.appendChild(imageCell);

    return tr;
  }

  var createTableRowForNoCachedData=function() {
    var tr=document.createElement("tr");
    var td=document.createElement("td");
    td.setAttribute("colspan","4");
    td.appendChild(document.createTextNode("No connectivity or cached data.  Please try again later."));
    tr.appendChild(td);
    return tr;
  }

  var createTableRowForNoData=function() {
    var tr=document.createElement("tr");
    var td=document.createElement("td");
    td.setAttribute("colspan","4");
    td.appendChild(document.createTextNode("No reviews."));
    tr.appendChild(td);
    return tr;
  }

  var getElapsedTime=function(oldSeconds,newSeconds) {
    var display="";
    var seconds=newSeconds-oldSeconds;
    if (seconds<60){
      display=Math.round(seconds)+" sec";
    } else {
      var minutes=seconds/60;
      if (minutes<60) {
        display=Math.round(minutes)+" min";
      } else {
        var hours=minutes/60;
        if (hours<24) {
          display=Math.round(hours)+" hr";
        } else {
          var days=hours/24;
          if (days<30) {
            display=Math.round(days)+" days";
          } else {
            var months=days/30;
            if (months<12) {
              display=Math.round(months)+" months";
            } else {
              var years=months/12;
              display=Math.round(years)+" years";
            }
          }
        }
      }
    }
    return display;
  }

  var displayTableNoCachedData=function() {
    document.getElementById("waitingForData").style.display="none";
    document.getElementById("moreIndicator").style.display="none";
    var table=document.getElementById("reviews");
    if (table==null) {
      table=createTable();
      document.getElementById("questionData").appendChild(table);
    }
    table.appendChild(createTableRowForNoCachedData());
  }

  var reloadQuestionesPage=function() {
    window.location='/stores?questionId='+questionId;
    return false;
  }

  return {
    display: function(aQuestionId, aReviewId) {
      questionId=aQuestionId;
      reviewId=aReviewId;
      create();
    },
    linkTo: function(aQuestionId, aReviewId) {
      questionId=aQuestionId;
      reviewId=aReviewId;
      QuestionRev.lock=false;
      if (reviewId) {
        var stateObj = { action: "review", questionId: questionId, reviewId: reviewId };
        history.pushState(stateObj, "Review", "/stores?reviewId=" + reviewId );
      } else {
        var stateObj = { action: "question", questionId: questionId };
        history.pushState(stateObj, "Question", "/stores?questionId=" + questionId );
      }
      create();
    },
    postReviewToFacebook: function(aReviewId) {
      postReviewToFacebook(aReviewId);
    }
  };

})();