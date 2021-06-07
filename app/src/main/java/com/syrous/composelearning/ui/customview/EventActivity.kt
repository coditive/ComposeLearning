package com.syrous.composelearning.ui.customview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class EventActivity: AppCompatActivity() {


}

private val sampleEvents = listOf(
    Event(
        name = "Google I/O Keynote",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2021-05-18T13:00:00"),
        end = LocalDateTime.parse("2021-05-18T15:00:00"),
        description = "Tune in to find out about how we're furthering our mission to organize the world’s information and make it universally accessible and useful.",
    ),
    Event(
        name = "Developer Keynote",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2021-05-18T15:15:00"),
        end = LocalDateTime.parse("2021-05-18T16:00:00"),
        description = "Learn about the latest updates to our developer products and platforms from Google Developers.",
    ),
    Event(
        name = "What's new in Android",
        color = Color(0xFF1B998B),
        start = LocalDateTime.parse("2021-05-18T16:50:00"),
        end = LocalDateTime.parse("2021-05-18T17:00:00"),
        description = "In this Keynote, Chet Haase, Dan Sandler, and Romain Guy discuss the latest Android features and enhancements for developers.",
    ),
)


val EventTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

@Composable
fun BasicEvent(
    event: Event,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(event.color, shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        Text(text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(
            EventTimeFormatter)}")

        Text(text = event.name, fontWeight = FontWeight.Bold)

        if(event.description != null) {
            Text(text = event.description, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun BasicDayHeader(
    day: LocalDate,
    modifier: Modifier
) {

}

class EventsProvider : PreviewParameterProvider<Event> {
    override val values = sampleEvents.asSequence()
}

@Composable
fun Schedule(
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
    maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate()
) {
    val hourHeight = 64.dp
    val dayWidth = 256.dp
    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    Layout(content = {
                     events.sortedBy(Event::start).forEach{
                         event ->
                         Box(modifier = Modifier.eventData(event = event)) {
                             eventContent(event)
                         }
                     }
    }, modifier = modifier
        .verticalScroll(rememberScrollState())
        .horizontalScroll(rememberScrollState())
    ) {
        measureables, constraints ->

        val height = hourHeight.roundToPx() * 24
        val width = dayWidth.roundToPx() * numDays
        val placeableWithEvents = measureables.map {
            measurable ->
            val event = measurable.parentData as Event
            val eventDurationMin =  ChronoUnit.MINUTES.between(event.start, event.end)
            val eventHeight = ((eventDurationMin / 60f) * hourHeight.roundToPx()).roundToInt()
            val placeable = measurable.measure(constraints.copy(minWidth = dayWidth.roundToPx(), maxWidth = dayWidth.roundToPx(), minHeight = eventHeight, maxHeight = eventHeight))
            Pair(placeable, event)
        }

        layout(width, height) {
            placeableWithEvents.forEach { (placeable, event) ->
                val eventOffsetMin = ChronoUnit.MINUTES.between(LocalDateTime.MIN, event.start.toLocalTime())
                val eventY = ((eventOffsetMin / 60f) * hourHeight.toPx()).roundToInt()
                val eventOffsetDays = ChronoUnit.DAYS.between(minDate, event.start.toLocalDate()).toInt()
                val eventX = eventOffsetDays * dayWidth.roundToPx()
                placeable.place(eventX, eventY)
            }
        }
    }
}

private class EventDataModifier(
    val event: Event
): ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = event
}

private fun Modifier.eventData(event: Event) = this.then(EventDataModifier(event))


@Preview(showBackground = true)
@Composable
fun EventPreview(
    @PreviewParameter(EventsProvider::class) event: Event,
) {
    BasicEvent(event, modifier = Modifier.sizeIn(maxHeight = 64.dp))
}

@Preview(showBackground = true)
@Composable
fun SchedulePreview() {
    Schedule(events = sampleEvents)
}

