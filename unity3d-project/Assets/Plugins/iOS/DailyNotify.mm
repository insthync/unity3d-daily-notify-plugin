/*
 * Copyright (C) 2014 Ittipon Teerapruettikulchai
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

#import <UIKit/UIKit.h>

extern UIViewController *UnityGetGLViewController();
extern "C" void UnitySendMessage(const char *, const char *, const char *);

@interface DailyNotifyPlugin : NSObject
{
}
@end

@implementation DailyNotifyPlugin

- (id)init
{
	self = [super init];
    
	return self;
}

- (void)setNotifyAppearance:(const char *)title content:(const char *)content
{
    NSCalendar *calendar;
    UILocalNotification *notification;
    NSDateComponents *componentsForFireDate;
    NSDate *fireDateOfNotification;
    
    // Create the notifications
    [self removeNotify: @"1"];
    [self removeNotify: @"2"];
    
    // First
    calendar = [NSCalendar autoupdatingCurrentCalendar];
    
    componentsForFireDate = [calendar components:(NSYearCalendarUnit | NSHourCalendarUnit | NSMinuteCalendarUnit| NSSecondCalendarUnit ) fromDate: [NSDate date]];
    [componentsForFireDate setHour: 11];
    [componentsForFireDate setMinute:59];
    [componentsForFireDate setSecond:59];
    
    fireDateOfNotification = [calendar dateFromComponents: componentsForFireDate];
    
    notification = [[UILocalNotification alloc]  init];
    notification.fireDate = fireDateOfNotification;
    notification.timeZone = [NSTimeZone localTimeZone];
    notification.alertBody = [NSString stringWithUTF8String: content];
    notification.alertAction = @"go back";
    notification.userInfo= [NSDictionary dictionaryWithObject:[NSString stringWithFormat:@"1"] forKey:@"uid"];
    notification.repeatInterval= NSDayCalendarUnit;
    notification.soundName = UILocalNotificationDefaultSoundName;
    notification.applicationIconBadgeNumber = 1;
    [[UIApplication sharedApplication] scheduleLocalNotification:notification];
    
    // Second    calendar = [NSCalendar autoupdatingCurrentCalendar];
    
    componentsForFireDate = [calendar components:(NSYearCalendarUnit | NSHourCalendarUnit | NSMinuteCalendarUnit| NSSecondCalendarUnit ) fromDate: [NSDate date]];
    [componentsForFireDate setHour: 23];
    [componentsForFireDate setMinute:59];
    [componentsForFireDate setSecond:59];
    
    fireDateOfNotification = [calendar dateFromComponents: componentsForFireDate];
    
    notification = [[UILocalNotification alloc]  init];
    notification.fireDate = fireDateOfNotification;
    notification.timeZone = [NSTimeZone localTimeZone];
    notification.alertBody = [NSString stringWithUTF8String: content];
    notification.alertAction = @"go back";
    notification.userInfo= [NSDictionary dictionaryWithObject:[NSString stringWithFormat:@"2"] forKey:@"uid"];
    notification.repeatInterval= NSDayCalendarUnit;
    notification.soundName = UILocalNotificationDefaultSoundName;
    notification.applicationIconBadgeNumber = 1;
    [[UIApplication sharedApplication] scheduleLocalNotification:notification] ;
}

- (void)removeNotify:(NSString *)uidtodelete
{
    UIApplication *app = [UIApplication sharedApplication];
    NSArray *eventArray = [app scheduledLocalNotifications];
    for (int i=0; i<[eventArray count]; i++)
    {
        UILocalNotification* oneEvent = [eventArray objectAtIndex:i];
        NSDictionary *userInfoCurrent = oneEvent.userInfo;
        NSString *uid = [NSString stringWithFormat:@"%@",[userInfoCurrent valueForKey:@"uid"]];
        if ([uid isEqualToString:uidtodelete])
        {
            //Cancelling local notification
            [app cancelLocalNotification:oneEvent];
            break;
        }
    }
}

@end

extern "C" {
	void *_DailyNotifyPlugin_Init();
	void _DailyNotifyPlugin_SetNotifyAppearance(void* instace, const char *title, const char *content);
}

void *_DailyNotifyPlugin_Init()
{
	id instance = [[DailyNotifyPlugin alloc] init];
	return (void *)instance;
}

void _DailyNotifyPlugin_SetNotifyAppearance(void* instance, const char *title, const char *content)
{
    DailyNotifyPlugin* dailyNotifyPlugin = (DailyNotifyPlugin*)instance;
    [dailyNotifyPlugin setNotifyAppearance:title content:content];
}
